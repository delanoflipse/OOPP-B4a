public class Question {
    String text;
    Answer[] answers;

    public Question(String text, Answer[] answer) {
        this.text = text;
        this.answers = answer;
        if (!onlyOneValid()) {
            System.out.println("More than 1 valid answer!");
        }
    }

    private boolean onlyOneValid () {
        int correct = 0;
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].correct) {
               correct++;
            }
        }

        return correct == 1;
    }

    public Answer getCorrectAnswer() {
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].correct) {
                return answers[i];
            }
        }

        return null;
    }
}
