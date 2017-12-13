package main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseParser {
    String filename;

    public DatabaseParser(String filename) {
        this.filename = filename;
    }

    public ArrayList<Question> parse() {
        ArrayList<Question> questions = new ArrayList<>();
        Question currentQuestion = null;
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                if (currentQuestion == null) {
                    currentQuestion = baseLineParse(line);
                    continue;
                }
                boolean cont = true;
                if (currentQuestion instanceof TextQuestion) {
                    cont = textQuestionLineParse((TextQuestion) currentQuestion, line);
                } else if (currentQuestion instanceof OpenQuestion) {
                    cont = openQuestionLineParse((OpenQuestion) currentQuestion, line);
                } else if (currentQuestion instanceof ImageQuestion) {
                    cont = imageQuestionLineParse((ImageQuestion) currentQuestion, line);
                }

                if (!cont) {
                    questions.add(currentQuestion);
                    currentQuestion = null;
                }
            }

            if (currentQuestion != null) {
                questions.add(currentQuestion);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong reading the file");
        }

        return questions;
    }

    private KeyValuePair splitLine(String line) {
        if (line == null || line.length() == 0) {
            return null;
        }

        if (line.charAt(0) == '#') {
            return null;
        }

        int index = line.indexOf(':');

        if (index == -1) {
            return null;
        }

        return new KeyValuePair(
          line.substring(0, index).trim(),
          line.substring(index + 1, line.length()).trim()
        );

    }

    private Question baseLineParse(String line) {
        KeyValuePair parts = splitLine(line);

        if (parts == null) {
            return null;
        }

        if (parts.key.equals("type")) {
            switch (parts.value) {
                case "text":
                    return new TextQuestion();
                case "open":
                    return new OpenQuestion();
                case "image":
                    return new ImageQuestion();
                default:
                    return null;
            }
        }

        return null;

    }

    private boolean textQuestionLineParse(TextQuestion question, String line) {
        if (line.equals("")) {
            return false;
        }

        if (question == null) {
            System.out.println("no question");
            return true;
        }

        KeyValuePair parts = splitLine(line);

        if (parts == null) {
            return true;
        }

        switch (parts.key) {
            case "level":
                question.level = parts.valueAsInt();
                break;

            case "question":
                question.text = parts.value;
                break;

            case "answer":
                TextAnswer answer = new TextAnswer();
                answer.text = parts.value;
                question.addAnswer(answer);
                break;

            case "correctanswer":
                TextAnswer correctanswer = new TextAnswer();
                correctanswer.text = parts.value;
                correctanswer.correct = true;
                question.addAnswer(correctanswer);
                break;
        }

        return true;
    }

    private boolean imageQuestionLineParse(ImageQuestion question, String line) {
        if (line.equals("")) {
            return false;
        }

        if (question == null) {
            System.out.println("no question");
            return true;
        }

        KeyValuePair parts = splitLine(line);

        if (parts == null) {
            return true;
        }

        switch (parts.key) {
            case "level":
                question.level = parts.valueAsInt();
                break;

            case "question":
                question.text = parts.value;
                break;

            case "image":
                question.image = parts.value;
                break;

            case "bottomright":
                Position posbr = new Position(parts.value);
                question.setBottomRight(posbr.x, posbr.y);
                break;

            case "topleft":
                Position postf = new Position(parts.value);
                question.setBottomRight(postf.x, postf.y);
                break;
        }

        return true;
    }

    private boolean openQuestionLineParse(OpenQuestion question, String line) {
        if (line.equals("")) {
            return false;
        }

        if (question == null) {
            System.out.println("no question");
            return true;
        }

        KeyValuePair parts = splitLine(line);

        if (parts == null) {
            return true;
        }

        switch (parts.key) {
            case "level":
                question.level = parts.valueAsInt();
                break;

            case "question":
                question.text = parts.value;
                break;

            case "correctanswer":
                question.setCorrectAnswer(parts.value);
                break;
        }

        return true;
    }

}

class KeyValuePair {
    String key;
    String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public int valueAsInt() {
        return Integer.parseInt(value.replaceAll("[^\\d]", ""));
    }
}

class Position {
    int x = 0;
    int y = 0;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(String value) {
        String[] arr = value.trim().split(",");
        this.x = Integer.parseInt(arr[0].replaceAll("[^\\d]", ""));
        this.y = Integer.parseInt(arr[1].replaceAll("[^\\d]", ""));
    }

}