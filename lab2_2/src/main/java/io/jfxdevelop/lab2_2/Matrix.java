package io.jfxdevelop.lab2_2;

import java.util.Arrays;
import java.util.Objects;

public class Matrix {
    private final int rows;
    private final int cols;
    private final double[][] data;

    // Конструктор для создания матрицы заданного размера
    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Размеры матрицы должны быть положительными.");
        }
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    // Конструктор для создания матрицы из двумерного массива
    public Matrix(double[][] data) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Матрица не может быть пустой.");
        }
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            if (data[i].length != cols) {
                throw new IllegalArgumentException("Все строки матрицы должны иметь одинаковую длину.");
            }
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }

    // Геттеры
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double get(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Индексы вне границ матрицы.");
        }
        return data[row][col];
    }

    public void set(int row, int col, double value) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Индексы вне границ матрицы.");
        }
        data[row][col] = value;
    }

    // Операция сложения
    public Matrix add(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Матрицы должны иметь одинаковые размеры для сложения.");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] + other.data[i][j];
            }
        }
        return result;
    }

    // Операция умножения
    public Matrix multiply(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Количество столбцов первой матрицы должно равняться количеству строк второй матрицы.");
        }
        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                double sum = 0.0;
                for (int k = 0; k < this.cols; k++) {
                    sum += this.data[i][k] * other.data[k][j];
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        int[] colWidths = new int[cols];
        for (int j = 0; j < cols; j++) {
            int maxWidth = 0;
            for (int i = 0; i < rows; i++) {
                String cell = String.format("%.2f", data[i][j]);
                if (cell.length() > maxWidth) {
                    maxWidth = cell.length();
                }
            }
            colWidths[j] = maxWidth;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String cell = String.format("%.2f", data[i][j]);
                sb.append(String.format("%-" + colWidths[j] + "s", cell));
                if (j < cols - 1) {
                    sb.append("  "); // Два пробела между столбцами для читаемости
                }
            }
            if (i < rows - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return rows == matrix.rows && cols == matrix.cols && Arrays.deepEquals(data, matrix.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, cols, Arrays.deepHashCode(data));
    }
}