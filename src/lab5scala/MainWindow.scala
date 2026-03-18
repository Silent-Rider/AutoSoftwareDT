package lab5scala

import java.awt.*
import java.awt.event.ActionEvent
import javax.swing.*

class MainWindow extends JFrame("Редактор полиморфного списка") {

  private val factory: UserFactory = new UserFactory()
  
  private var dataList: UniversalLinkedList[Any] = null
  private var currentPrototype: UserType[?] = null

  private var typeComboBox: JComboBox[String] = null
  private var inputField: JTextField = null
  private var addButton: JButton = null
  private var sortButton: JButton = null
  private var clearButton: JButton = null
  private var aboutButton: JButton = null
  private var outputArea: JTextArea = null
  private var statusLabel: JLabel = null

  setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
  setSize(700, 600)
  setLocationRelativeTo(null)
  
  initComponents()
  layoutComponents()
  addListeners()

  val item: AnyRef = typeComboBox.getSelectedItem
  if (item != null) {
    selectType(item.asInstanceOf[String])
  }

  private def initComponents(): Unit = {
    typeComboBox = new JComboBox[String]()
    factory.getTypeNameList.foreach(tp => typeComboBox.addItem(tp))

    inputField = new JTextField(22)

    addButton = new JButton("Добавить")
    sortButton = new JButton("Сортировать")
    clearButton = new JButton("Очистить")
    aboutButton = new JButton("О программе")

    outputArea = new JTextArea()
    outputArea.setEditable(false)
    outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14))

    statusLabel = new JLabel()
    statusLabel.setBorder(BorderFactory.createEtchedBorder())
    
    dataList = new UniversalLinkedList[Any](null)
  }

  private def layoutComponents(): Unit = {
    val topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT))
    topPanel.add(new JLabel("Тип данных: "))
    topPanel.add(typeComboBox)
    topPanel.add(Box.createHorizontalStrut(20))
    topPanel.add(new JLabel("Значение: "))
    topPanel.add(inputField)
    topPanel.add(addButton)

    val sortAndClearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT))
    sortAndClearPanel.add(sortButton)
    sortAndClearPanel.add(clearButton)

    val aboutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT))
    aboutPanel.add(aboutButton)

    val buttonPanel = new JPanel(new BorderLayout())
    buttonPanel.add(sortAndClearPanel, BorderLayout.WEST)
    buttonPanel.add(aboutPanel, BorderLayout.EAST)

    val centerPanel = new JPanel(new BorderLayout())
    centerPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER)
    centerPanel.add(buttonPanel, BorderLayout.SOUTH)

    setLayout(new BorderLayout())
    add(topPanel, BorderLayout.NORTH)
    add(centerPanel, BorderLayout.CENTER)
    add(statusLabel, BorderLayout.SOUTH)
  }

  private def addListeners(): Unit = {
    typeComboBox.addActionListener((e: ActionEvent) => {
      val selected = typeComboBox.getSelectedItem
      if (selected != null) {
        selectType(selected.asInstanceOf[String])
      }
    })
    
    addButton.addActionListener((e: ActionEvent) => tryAddElement())
    inputField.addActionListener((e: ActionEvent) => tryAddElement())
    sortButton.addActionListener((e: ActionEvent) => performSort())
    
    val aboutText =
      """Программа была разработана
        |студентами группы АПИМ-25
        |Клименко Кириллом и Бадаговым Андреем""".stripMargin

    aboutButton.addActionListener((e: ActionEvent) => {
      JOptionPane.showMessageDialog(
        null,
        aboutText,
        "О программе",
        JOptionPane.INFORMATION_MESSAGE
      )
    })
    
    clearButton.addActionListener((e: ActionEvent) => {
      dataList = new UniversalLinkedList[Any](currentPrototype.asInstanceOf[UserType[Any]])
      updateOutput()
      statusLabel.setText("Список очищен")
    })
  }

  private def selectType(typeName: String): Unit = {
    if (typeName == null) return

    currentPrototype = factory.getBuilderByName(typeName)
    dataList = new UniversalLinkedList[Any](currentPrototype.asInstanceOf[UserType[Any]])

    inputField.setText("")
    updateOutput()
    statusLabel.setText(s"Выбран тип: $typeName")
  }

  private def tryAddElement(): Unit = {
    val text = inputField.getText.trim
    if (text.isEmpty) {
      statusLabel.setText("Ошибка: введите значение")
      return
    }
    
    try {
      val value = currentPrototype.parseValue(text)

      dataList.addLast(value)

      inputField.setText("")
      updateOutput()
      statusLabel.setText(s"Добавлено: $value")
    } catch {
      case _: NumberFormatException =>
        statusLabel.setText(s"Ошибка формата числа для типа ${currentPrototype.typeName}")
      case e: Exception =>
        statusLabel.setText(s"Ошибка: ${e.getMessage}")
    }
  }

  private def performSort(): Unit = {
    if (dataList.getSize < 2) {
      statusLabel.setText("Сортировка не требуется (менее 2 элементов)")
      return
    }
    dataList.sort()
    updateOutput()
    statusLabel.setText("Список отсортирован")
  }

  private def updateOutput(): Unit = {
    outputArea.setText("")
    if (dataList.getSize == 0) {
      outputArea.append("Список пуст")
      return
    }
    
    dataList.forEach((value: Any) => {
      outputArea.append(value.toString + "\n")
    })
  }
}
