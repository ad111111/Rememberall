public class QuestionsList {
    private final int numberQuestions; // number of questions on quiz
    // symbol table with keys String questions and values answers as arrays
    // of Strings
    private ST<String, String[]> questions;

    // create a questions list object represented as a symbol table
    // with the questions as keys and array of answers as values
    // based on an array of all questions and all answers
    public QuestionsList(int numberQuestions, String[] questions, String[] answers) {
        this.questions = new ST<String, String[]>();
        this.numberQuestions = numberQuestions;

        // iterate through all questions, assigning each question as a key in
        // the symbol table and assigning answers as values
        for (int i = 0; i < this.numberQuestions; i++) {
            int ANSWERS_LENGTH = 4;
            String[] answersCopy = new String[ANSWERS_LENGTH];
            for (int j = 0; j < 4; j++) {
                answersCopy[j] = answers[4 * i + j];
            }
            this.questions.put(questions[i], answersCopy);
        }
    }

    // return a String array of answers to the given question
    public String[] getAnswers(String question) {
        String[] answers = questions.get(question);
        return answers;
    }

    // returns a string representation of the symbol table
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String question : questions) {
            sb.append(question + " \n");
            sb.append("Answers: ");
            String[] answers = questions.get(question);
            for (int i = 0; i < answers.length; i++) {
                sb.append(answers[i] + " ");
            }
            sb.append("\n \n");
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String[] sampleQuestions = {
                "What is 1 + 1?",
                "Who was the fifth president of the United States?"
        };
        String[] sampleAnswers = {
                "Two", "Three", "Four", "Five",
                "John Quincy Adams", "George Washington", "Thomas Jefferson",
                "John Adams"
        };
        QuestionsList testQuestionsList = new QuestionsList(2, sampleQuestions, sampleAnswers);

        String question = "Who was the fifth president of the United States?";
        StdOut.println("The possible answers to the above question are: ");
        String[] potentialAnswers = testQuestionsList.getAnswers(question);
        for (int i = 0; i < potentialAnswers.length; i++) {
            StdOut.println(potentialAnswers[i]);
        }

        StdOut.println("Full Questions List: ");
        StdOut.println(testQuestionsList);
    }
}
