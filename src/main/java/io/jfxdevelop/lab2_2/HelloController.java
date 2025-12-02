package io.jfxdevelop.lab2_2;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HelloController {

    @FXML
    private TextArea matrixAInput;

    @FXML
    private TextArea matrixBInput;

    @FXML
    private TextArea resultArea;

    @FXML
    protected void onAddClick() {
        try {
            Matrix matrixA = parseMatrix(matrixAInput.getText());
            Matrix matrixB = parseMatrix(matrixBInput.getText());
            Matrix result = matrixA.add(matrixB);
            resultArea.setText("Результат сложения:\n" + result.toString());
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    protected void onMultiplyClick() {
        try {
            Matrix matrixA = parseMatrix(matrixAInput.getText());
            Matrix matrixB = parseMatrix(matrixBInput.getText());
            Matrix result = matrixA.multiply(matrixB);
            resultArea.setText("Результат умножения:\n" + result.toString());
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    // Вспомогательный метод для парсинга матрицы из текста
    private Matrix parseMatrix(String inputText) throws Exception {
        if (inputText == null || inputText.trim().isEmpty()) {
            throw new Exception("Поле матрицы не может быть пустым.");
        }

        String[] lines = inputText.trim().split("\\r?\\n");
        if (lines.length == 0) {
            throw new Exception("Не найдено ни одной строки.");
        }

        // Определяем количество столбцов по первой строке
        String[] firstRowElements = lines[0].trim().split("\\s+");
        int cols = firstRowElements.length;
        if (cols == 0) {
            throw new Exception("Первая строка не содержит элементов.");
        }

        int rows = lines.length;
        double[][] data = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            String[] elements = lines[i].trim().split("\\s+");
            if (elements.length != cols) {
                throw new Exception("Строка " + (i + 1) + " содержит " + elements.length +
                        " элементов, а должно быть " + cols + ".");
            }
            for (int j = 0; j < cols; j++) {
                try {
                    data[i][j] = Double.parseDouble(elements[j]);
                } catch (NumberFormatException e) {
                    throw new Exception("Элемент в строке " + (i + 1) + ", столбце " + (j + 1) +
                            " не является числом: '" + elements[j] + "'.");
                }
            }
        }

        return new Matrix(data);
    }

    // Метод для отображения ошибок
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}