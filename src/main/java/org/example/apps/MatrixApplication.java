package org.example.apps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@SpringBootApplication
@Controller
public class MatrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatrixApplication.class, args);
    }

    @GetMapping("/")
    public String showMatrix(Model model) {
        int width = 25;
        int height = 15;
        char[][] matrix = generateMatrix(width, height);
        markPlus(matrix);
        String answer = findAnswer(matrix);
        printMatrix(matrix);

        System.out.println("Answer: " + answer);

        model.addAttribute("matrix", matrix);
        model.addAttribute("answer", answer);

        return "matrix";
    }


    public static char[][] generateMatrix(int width, int height) {
        char[][] matrix = new char[height][width];
        Random random = new Random();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int randomValue = random.nextInt(2);
                matrix[i][j] = (randomValue == 1) ? '1' : '0';
            }
        }
        return matrix;
    }

    public static void markPlus(char[][] matrix) {
        int width = matrix[0].length;
        int height = matrix.length;

        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (matrix[i][j] == '1') {
                    // Check if the surrounding elements form a plus shape
                    if (matrix[i - 1][j] == '1' && matrix[i + 1][j] == '1' &&
                            matrix[i][j - 1] == '1' && matrix[i][j + 1] == '1') {
                        // Mark the plus shape with '*'
                        matrix[i][j] = '*';
                        matrix[i - 1][j] = '*';
                        matrix[i + 1][j] = '*';
                        matrix[i][j - 1] = '*';
                        matrix[i][j + 1] = '*';
                    }
                }
            }
        }
    }

    public static String convertMatrixToString(char[][] matrix) {
        StringBuilder matrixOutput = new StringBuilder();
        for (char[] row : matrix) {
            for (char element : row) {
                matrixOutput.append(element);
            }
            matrixOutput.append("\n");
        }
        return matrixOutput.toString();
    }

    public static void printMatrix(char[][] matrix) {
        for (char[] row : matrix) {
            for (char element : row) {
                System.out.print(element);
            }
            System.out.println();
        }
    }

    public static String findAnswer(char[][] matrix) {
        StringBuilder answer = new StringBuilder();
        int width = matrix[0].length;
        int height = matrix.length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] == '*') {
                    answer.append("(").append(j).append(",").append(i).append(")\n");
                }
            }
        }

        return answer.toString();
    }
}
