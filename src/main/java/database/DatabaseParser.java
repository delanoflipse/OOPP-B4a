package database;

import user.UserData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Parse the database file
 */
public class DatabaseParser {
    String filename;

    public DatabaseParser(String filename) {
        this.filename = filename;
    }

    private enum StateValue {
        VALUES, QUESTIONS,
    }
    StateValue state = StateValue.VALUES;

    /**
     * Parse the database file
     * @return A list of questions for the database
     */
    public void parse() {
        // final object
        ArrayList<Question> questions = new ArrayList<>();
        // current question
        Question currentQuestion = null;
        // current line of the database file
        String line;
        state = StateValue.VALUES;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // for each line in the file
            while ((line = br.readLine()) != null) {
                // if not currently modifying a question
                if (currentQuestion == null) {
                    // check we need to modify
                    currentQuestion = baseLineParse(line);
                    continue;
                }

                // parse line based on type
                boolean cont = true;
                if (currentQuestion instanceof TextQuestion) {
                    cont = textQuestionLineParse((TextQuestion) currentQuestion, line);
                } else if (currentQuestion instanceof OpenQuestion) {
                    cont = openQuestionLineParse((OpenQuestion) currentQuestion, line);
                } else if (currentQuestion instanceof ClickQuestion) {
                    cont = imageQuestionLineParse((ClickQuestion) currentQuestion, line);
                } else if (currentQuestion instanceof GUIQuestion) {
                    cont = guiQuestionLineParse((GUIQuestion) currentQuestion, line);
                }

                // newline? -> end of question data
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

        Database.questions = questions;

        File folder = new File(Paths.get(".").toAbsolutePath().normalize().toString());
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> userfiles = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".userdata")) {
                String filename = file.getName();
//                String noExt = filename.substring(0, filename.length() - 10);
//                String name = noExt.substring(0, 1).toUpperCase() + noExt.substring(1);
                Database.users.add(UserData.parse(filename));
            }
        }
    }

    /**
     * Parse line to check for a new question
     * @param line current line from the database
     * @return A question or null
     */
    private Question baseLineParse(String line) {
        KeyValuePair parts = KeyValuePair.splitLine(line);

        if (parts == null) {
            return null;
        }

        if (parts.key.equals("type")) {
            switch (parts.value) {
                case "multiple choice":
                    return new TextQuestion();
                case "open":
                    return new OpenQuestion();
                case "clickable":
                    return new ClickQuestion();
                case "gui":
                    return new GUIQuestion();
                default:
                    return null;
            }
        }

        return null;

    }

    /**
     * Parse a text question
     * @param question Current question
     * @param line The current line of the database
     * @return If the parser should continue to parse for this question
     */
    private boolean textQuestionLineParse(TextQuestion question, String line) {
        if (line.equals("")) {
            return false;
        }

        if (question == null) {
            System.out.println("no question");
            return true;
        }

        KeyValuePair parts = KeyValuePair.splitLine(line);

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

    /**
     * Parse an image question
     * @param question Current question
     * @param line The current line of the database
     * @return If the parser should continue to parse for this question
     */
    private boolean imageQuestionLineParse(ClickQuestion question, String line) {
        if (line.equals("")) {
            return false;
        }

        if (question == null) {
            System.out.println("no question");
            return true;
        }

        KeyValuePair parts = KeyValuePair.splitLine(line);

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
                question.setBottomRight(posbr);
                break;

            case "topleft":
                Position postf = new Position(parts.value);
                question.setTopLeft(postf);
                break;
        }

        return true;
    }

    /**
     * Parse an open question
     * @param question Current question
     * @param line The current line of the database
     * @return If the parser should continue to parse for this question
     */
    private boolean openQuestionLineParse(OpenQuestion question, String line) {
        if (line.equals("")) {
            return false;
        }

        if (question == null) {
            System.out.println("no question");
            return true;
        }

        KeyValuePair parts = KeyValuePair.splitLine(line);

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

    /**
     * Parse a text question
     * @param question Current question
     * @param line The current line of the database
     * @return If the parser should continue to parse for this question
     */
    private boolean guiQuestionLineParse(GUIQuestion question, String line) {
        if (line.equals("")) {
            return false;
        }

        if (question == null) {
            System.out.println("no question");
            return true;
        }

        KeyValuePair parts = KeyValuePair.splitLine(line);

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
            case "menu":
                question.answers.add(new DropDownHead(parts.value));
                break;
            case "element":
                DropDownHead tempmenu = (DropDownHead) question.answers.get(question.answers.size()-1);
                tempmenu.getElements().add(new Element(parts.value));
                break;
            case "correctelement":
                DropDownHead tempmenu2 = (DropDownHead) question.answers.get(question.answers.size()-1);
                Element element = new Element(parts.value);
                element.correct = true;
                tempmenu2.getElements().add(element);
                break;
        }
        return true;
    }

}

