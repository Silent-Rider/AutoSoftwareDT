package lab4;

import lab2.UniversalLinkedList;
import lab3.UserType;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final UserFactory factory = new UserFactory();
    @SuppressWarnings("rawtypes")
    private UniversalLinkedList dataList;
    @SuppressWarnings("rawtypes")
    private UserType currentPrototype;

    private JComboBox<String> typeComboBox;
    private JTextField inputField;
    private JButton addButton;
    private JButton sortButton;
    private JButton clearButton;
    private JButton aboutButton;
    private JTextArea outputArea;
    private JLabel statusLabel;

    public MainWindow() {
        super("Редактор полиморфного списка");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);

        initComponents();
        layoutComponents();
        addListeners();

        selectType((String) typeComboBox.getSelectedItem());
    }

    private void initComponents() {
        typeComboBox = new JComboBox<>();
        factory.getTypeNameList().forEach(typeComboBox::addItem);

        inputField = new JTextField(22);

        addButton = new JButton("Добавить");
        sortButton = new JButton("Сортировать");
        clearButton = new JButton("Очистить");
        aboutButton = new JButton("О программе");

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        statusLabel = new JLabel();
        statusLabel.setBorder(BorderFactory.createEtchedBorder());

        dataList = new UniversalLinkedList<>(null);
    }

    private void layoutComponents() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Тип данных: "));
        topPanel.add(typeComboBox);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(new JLabel("Значение: "));
        topPanel.add(inputField);
        topPanel.add(addButton);

        JPanel sortAndClearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sortAndClearPanel.add(sortButton);
        sortAndClearPanel.add(clearButton);
        JPanel aboutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        aboutPanel.add(aboutButton);
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(sortAndClearPanel, BorderLayout.WEST);
        buttonPanel.add(aboutPanel, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        typeComboBox.addActionListener(e -> selectType((String) typeComboBox.getSelectedItem()));
        addButton.addActionListener(e -> tryAddElement());
        inputField.addActionListener(e -> tryAddElement());
        sortButton.addActionListener(e -> performSort());

        String aboutText = "Программа была разработана\nстудентами группы АПИМ-25\nКлименко Кириллом и Бадаговым Андреем";
        aboutButton.addActionListener(e ->
                JOptionPane.showMessageDialog(null, aboutText, "О программе",JOptionPane.INFORMATION_MESSAGE));

        clearButton.addActionListener(e -> {
            dataList = new UniversalLinkedList(currentPrototype);
            updateOutput();
            statusLabel.setText("Список очищен");
        });
    }

    private void selectType(String typeName) {
        if (typeName == null) return;

        currentPrototype = factory.getBuilderByName(typeName);
        dataList = new UniversalLinkedList(currentPrototype);

        inputField.setText("");
        updateOutput();
        statusLabel.setText("Выбран тип: " + typeName);
    }

    private void tryAddElement() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            statusLabel.setText("Ошибка: введите значение");
            return;
        }
        try {
            Object value = currentPrototype.parseValue(text);
            dataList.addLast(value);

            inputField.setText("");
            updateOutput();
            statusLabel.setText("Добавлено: " + value);
        } catch (NumberFormatException ex) {
            statusLabel.setText("Ошибка формата числа для типа " + currentPrototype.typeName());
        }
    }

    private void performSort() {
        if (dataList.size() < 2) {
            statusLabel.setText("Сортировка не требуется (менее 2 элементов)");
            return;
        }
        dataList.sort();
        updateOutput();
        statusLabel.setText("Список отсортирован");
    }

    private void updateOutput() {
        outputArea.setText("");
        if (dataList.size() == 0) {
            outputArea.append("Список пуст");
            return;
        }
        dataList.forEach(value -> {
            outputArea.append(value.toString() + "\n");
        });
        statusLabel.setText("Элементов в списке: " + dataList.size());
    }
}
