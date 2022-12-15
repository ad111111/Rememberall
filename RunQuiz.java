import java.awt.Color;
import java.awt.Font;

public class RunQuiz {
    // run the FORTUNE quiz based on the user input
    public static void main(String[] args) {
        // read number of quiz questions from StdIn
        int NUMBER_QUESTIONS = StdIn.readInt();
        StdIn.readLine();

        // create and populate questions array
        String[] questions = new String[NUMBER_QUESTIONS];
        int questionAnswerFactor = 4;
        String[] answers = new String[NUMBER_QUESTIONS * questionAnswerFactor];
        for (int i = 0; i < NUMBER_QUESTIONS; i++) {
            String question = StdIn.readLine();
            questions[i] = question;
            String answer1 = StdIn.readLine();
            answers[4 * i] = answer1;
            String answer2 = StdIn.readLine();
            answers[4 * i + 1] = answer2;
            String answer3 = StdIn.readLine();
            answers[4 * i + 2] = answer3;
            String answer4 = StdIn.readLine();
            answers[4 * i + 3] = answer4;

            // limit questions to 65 characters and answers to 25 characters
            int QUESTION_CHAR_LIMIT = 65;
            int ANSWER_CHAR_LIMIT = 25;
            if (question.length() > QUESTION_CHAR_LIMIT)
                throw new IllegalArgumentException("Please enter a shorter question.");
            if (answer1.length() > ANSWER_CHAR_LIMIT || answer2.length() > ANSWER_CHAR_LIMIT
                    || answer3.length() > ANSWER_CHAR_LIMIT
                    || answer4.length() > ANSWER_CHAR_LIMIT) {
                throw new IllegalArgumentException("Please enter shorter answer choices");
            }
        }

        // read happy fortune cookie training data from StdIn
        StringBuilder happySb = new StringBuilder();
        int numberHappy = StdIn.readInt();
        for (int i = 0; i < numberHappy; i++) {
            happySb.append(StdIn.readLine());
        }

        // read sad fortune cookie training data from StdIn
        StringBuilder sadSb = new StringBuilder();
        int numberSad = StdIn.readInt();
        for (int i = 0; i < numberSad; i++) {
            sadSb.append(StdIn.readLine());
        }

        // create new questions list based on what was read from StdIn
        QuestionsList quiz = new QuestionsList(NUMBER_QUESTIONS, questions, answers);

        // define start button parameters and create start button
        double START_X = 0.5;
        double START_Y = 0.10;
        double START_LENGTH = 0.2;
        double START_HEIGHT = 0.05;
        String START_BUTTON_TEXT = "START";
        Button startButton = new Button(START_X, START_Y,
                                        START_LENGTH, START_HEIGHT,
                                        START_BUTTON_TEXT);
        // draw button to screen
        startButton.buttonText();
        startButton.drawButton();

        // define next button parameters and create next button
        double NEXT_X = 0.70;
        double NEXT_Y = 0.10;
        double NEXT_LENGTH = 0.2;
        double NEXT_HEIGHT = 0.05;
        String NEXT_BUTTON_TEXT = "NEXT";
        Button nextButton = new Button(NEXT_X, NEXT_Y,
                                       NEXT_LENGTH, NEXT_HEIGHT,
                                       NEXT_BUTTON_TEXT);

        // define shop button parameters and create shop button
        double SHOP_X = 0.30;
        double SHOP_Y = 0.10;
        double SHOP_LENGTH = 0.2;
        double SHOP_HEIGHT = 0.05;
        String SHOP_BUTTON_TEXT = "SHOP";
        Button shopButton = new Button(SHOP_X, SHOP_Y,
                                       SHOP_LENGTH, SHOP_HEIGHT,
                                       SHOP_BUTTON_TEXT);

        // define back button parameters and create back button
        double BACK_X = 0.50;
        double BACK_Y = 0.15;
        double BACK_LENGTH = 0.2;
        double BACK_HEIGHT = 0.05;
        String BACK_BUTTON_TEXT = "BACK";
        Button backButton = new Button(BACK_X, BACK_Y,
                                       BACK_LENGTH, BACK_HEIGHT,
                                       BACK_BUTTON_TEXT);

        // define buy button parameters and create buy button
        double BUY_X = 0.50;
        double BUY_Y = 0.30;
        double BUY_LENGTH = 0.2;
        double BUY_HEIGHT = 0.05;
        String BUY_BUTTON_TEXT = "BUY";
        Button buyButton = new Button(BUY_X, BUY_Y,
                                      BUY_LENGTH, BUY_HEIGHT,
                                      BUY_BUTTON_TEXT);

        // set fortune cookie points to 0
        double fortuneCookiePoints = 0.0;

        // set display x and y coordinates for points value
        double POINTS_X = 0.85;
        double POINTS_Y = 0.92;

        // define dragon home screen gif parameters
        double DRAGON_X = 0.5;
        double DRAGON_Y = 0.5;
        double DRAGON_WIDTH = 1.2;
        double DRAGON_HEIGHT = 1.0;

        // draw dragon home screen gif to StdDraw until start button clicked
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.ORANGE);
        while (true) {
            StdDraw.pause(10);
            StdDraw.clear();
            StdDraw.picture(DRAGON_X, DRAGON_Y, "DragonWalking.gif", DRAGON_WIDTH, DRAGON_HEIGHT);
            startButton.buttonText();
            startButton.drawButton();
            StdDraw.pause(10);
            StdDraw.show();
            if (startButton.withinBoundaries()) break;
        }

        while (true) {
            // check whether start, next, or back button were clicked
            if (startButton.withinBoundaries() || nextButton.withinBoundaries()
                    || backButton.withinBoundaries()) {
                StdDraw.disableDoubleBuffering();
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.pause(100);
                StdDraw.clear(Color.ORANGE);

                // define parameters for quiz background and draw on StdDraw
                double BACKGROUND_X = 0.5;
                double BACKGROUND_Y = 0.52;
                StdDraw.picture(BACKGROUND_X, BACKGROUND_Y, "YellowLantersBackground.jpeg");

                // draw points text to StdDraw
                StdDraw.text(POINTS_X, POINTS_Y, String.valueOf(fortuneCookiePoints));

                // pick a random question and get a defensive copy of its answers
                // do not mutate original answers array
                String question = questions[(StdRandom.uniformInt(NUMBER_QUESTIONS))];
                String[] questionAnswers = quiz.getAnswers(question);
                String[] questionAnswersCopy = new String[questionAnswers.length];
                for (int k = 0; k < questionAnswers.length; k++) {
                    questionAnswersCopy[k] = questionAnswers[k];
                }

                // define correct answer as first answer input
                String correctAnswer = questionAnswers[0];

                // shuffle answers copy array for display on screen
                StdRandom.shuffle(questionAnswersCopy);

                // define question parameters and draw on StdDraw
                double questionX = 0.5;
                double questionY = 0.73;
                StdDraw.text(questionX, questionY, question);

                // define answer 1 button parameters, create answer 1 button,
                // and draw on StdDraw
                double ANSWER1_X = 0.3;
                double ANSWER1_Y = 0.5;
                double ANSWER1_LENGTH = 0.2;
                double ANSWER1_HEIGHT = 0.1;
                String answer1Text = questionAnswersCopy[0];
                Button answer1Button = new Button(ANSWER1_X, ANSWER1_Y,
                                                  ANSWER1_LENGTH, ANSWER1_HEIGHT,
                                                  answer1Text);
                answer1Button.buttonText();
                answer1Button.drawButton();

                // define answer 2 button parameters, create answer 2 button,
                // and draw on StdDraw
                double ANSWER2_X = 0.7;
                double ANSWER2_Y = 0.5;
                double ANSWER2_LENGTH = 0.2;
                double ANSWER2_HEIGHT = 0.1;
                String answer2Text = questionAnswersCopy[1];
                Button answer2Button = new Button(ANSWER2_X, ANSWER2_Y,
                                                  ANSWER2_LENGTH, ANSWER2_HEIGHT,
                                                  answer2Text);
                answer2Button.buttonText();
                answer2Button.drawButton();

                // define answer 3 button parameters, create answer 3 button,
                // and draw on StdDraw
                double ANSWER3_X = 0.3;
                double ANSWER3_Y = 0.3;
                double ANSWER3_LENGTH = 0.2;
                double ANSWER3_HEIGHT = 0.1;
                String answer3Text = questionAnswersCopy[2];
                Button answer3Button = new Button(ANSWER3_X, ANSWER3_Y,
                                                  ANSWER3_LENGTH, ANSWER3_HEIGHT,
                                                  answer3Text);
                answer3Button.buttonText();
                answer3Button.drawButton();

                // define answer 4 button parameters, create answer 4 button,
                // and draw on StdDraw
                double ANSWER4_X = 0.7;
                double ANSWER4_Y = 0.3;
                double ANSWER4_LENGTH = 0.2;
                double ANSWER4_HEIGHT = 0.1;
                String answer4Text = questionAnswersCopy[3];
                Button answer4Button = new Button(ANSWER4_X, ANSWER4_Y,
                                                  ANSWER4_LENGTH, ANSWER4_HEIGHT,
                                                  answer4Text);
                answer4Button.buttonText();
                answer4Button.drawButton();


                // set timer time limit to 30 seconds
                double timeLimit = 30;

                // create new timer with given time limit
                Timer questionTimer = new Timer(timeLimit);

                while (!(questionTimer.timeRemainingDouble() == -1.0)) {
                    // define timer parameters and draw to StdDraw until time displays 0
                    if (Timer.isIntValue(questionTimer.timeRemainingDouble(), timeLimit)) {
                        double TIMER_X = 0.15;
                        double TIMER_Y = 0.90;
                        double TIMER_LENGTH = 0.05;
                        double TIMER_HEIGHT = 0.025;
                        String TIMER_TEXT = questionTimer.timeRemaining();
                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.filledRectangle(TIMER_X, TIMER_Y, TIMER_LENGTH, TIMER_HEIGHT);
                        StdDraw.setPenColor(Color.WHITE);
                        StdDraw.text(TIMER_X, TIMER_Y, TIMER_TEXT);
                        StdDraw.setPenColor(Color.BLACK);
                    }

                    // check if answer 1 is selected and output to user or adjust points
                    if (answer1Button.withinBoundaries()) {
                        if (answer1Text.equals(correctAnswer)) {
                            StdDraw.text(0.5, 0.65, "Correct!");
                            fortuneCookiePoints += 100.00
                                    + (int) questionTimer.timeRemainingDouble();
                            break;
                        }
                        else {
                            StdDraw.text(0.5, 0.65, "Incorrect!");
                            break;
                        }
                    }

                    // check if answer 2 is selected and output to user or adjust points
                    if (answer2Button.withinBoundaries()) {
                        if (answer2Text.equals(correctAnswer)) {
                            StdDraw.text(0.5, 0.65, "Correct!");
                            fortuneCookiePoints += 100.00
                                    + (int) questionTimer.timeRemainingDouble();
                            break;
                        }
                        else {
                            StdDraw.text(0.5, 0.65, "Incorrect!");
                            break;
                        }
                    }

                    // check if answer 3 is selected and output to user or adjust points
                    if (answer3Button.withinBoundaries()) {
                        if (answer3Text.equals(correctAnswer)) {
                            StdDraw.text(0.5, 0.65, "Correct!");
                            fortuneCookiePoints += 100.00
                                    + (int) questionTimer.timeRemainingDouble();
                            break;
                        }
                        else {
                            StdDraw.text(0.5, 0.65, "Incorrect!");
                            break;
                        }
                    }

                    // check if answer 4 is selected and output to user or adjust points
                    if (answer4Button.withinBoundaries()) {
                        if (answer4Text.equals(correctAnswer)) {
                            StdDraw.text(0.5, 0.65, "Correct!");
                            fortuneCookiePoints += 100.00
                                    + (int) questionTimer.timeRemainingDouble();
                            break;
                        }
                        else {
                            StdDraw.text(0.5, 0.65, "Incorrect!");
                            break;
                        }
                    }
                }

                // display next button on screen
                nextButton.buttonText();
                nextButton.drawButton();
                shopButton.buttonText();
                shopButton.drawButton();
                StdDraw.pause(100);

                while (true) {
                    // check if shop or next button are selected
                    if (shopButton.withinBoundaries() || nextButton.withinBoundaries()) {
                        if (shopButton.withinBoundaries()) {
                            StdDraw.clear(Color.ORANGE);
                            StdDraw.pause(100);

                            // define shop background picture parameters and draw on StdDraw
                            double SHOP_BACK_X = 0.5;
                            double SHOP_BACK_Y = 0.45;
                            double SHOP_BACK_WIDTH = 1.5;
                            double SHOP_BACK_HEIGHT = 1.3;
                            StdDraw.picture(SHOP_BACK_X, SHOP_BACK_Y, "ShopBackground.jpg",
                                            SHOP_BACK_WIDTH, SHOP_BACK_HEIGHT);

                            StdDraw.setPenColor(Color.ORANGE);

                            // display fortune cookie points
                            StdDraw.text(POINTS_X, POINTS_Y, String.valueOf(fortuneCookiePoints));

                            // draw back and buy buttons on StdDraw
                            backButton.buttonText();
                            backButton.drawButton();
                            buyButton.buttonText();
                            buyButton.drawButton();

                            // define fortune cookie full picture parameters
                            // and display on screen
                            double FORTUNE_COOKIE_PICTURE_X = 0.5;
                            double FORTUNE_COOKIE_PICTURE_Y = 0.65;
                            double FORTUNE_COOKIE_WIDTH = 0.50;
                            double FORTUNE_COOKIE_HEIGHT = 0.40;
                            StdDraw.picture(FORTUNE_COOKIE_PICTURE_X,
                                            FORTUNE_COOKIE_PICTURE_Y,
                                            "Fortune Cookie Full.png", FORTUNE_COOKIE_WIDTH,
                                            FORTUNE_COOKIE_HEIGHT);

                            StdDraw.setPenColor(Color.BLACK);


                            while (true) {
                                // check if buy button is selected
                                if (buyButton.withinBoundaries()) {
                                    if (fortuneCookiePoints < 100) {
                                        StdDraw.pause(100);
                                        continue;
                                    }

                                    // re-draw the screen
                                    StdDraw.clear(Color.ORANGE);
                                    StdDraw.setPenColor(Color.ORANGE);
                                    StdDraw.picture(SHOP_BACK_X, SHOP_BACK_Y, "ShopBackground.jpg",
                                                    SHOP_BACK_WIDTH, SHOP_BACK_HEIGHT);
                                    StdDraw.text(POINTS_X, POINTS_Y,
                                                 String.valueOf(fortuneCookiePoints));
                                    backButton.buttonText();
                                    backButton.drawButton();
                                    buyButton.buttonText();
                                    buyButton.drawButton();

                                    // subtract points
                                    fortuneCookiePoints -= 100;

                                    // define split fortune cookie parameters and draw on StdDraw
                                    double SPLIT_FORTUNE_X = 0.53;
                                    double SPLIT_FORTUNE_Y = 0.65;
                                    double SPLIT_FORTUNE_WIDTH = 0.85;
                                    double SPLIT_FORTUNE_HEIGHT = 0.67;
                                    StdDraw.picture(SPLIT_FORTUNE_X,
                                                    SPLIT_FORTUNE_Y,
                                                    "SplitFortuneCookie.png",
                                                    SPLIT_FORTUNE_WIDTH,
                                                    SPLIT_FORTUNE_HEIGHT);

                                    // determine the category of markov text generated
                                    String text = MarkovModel.categoryText(fortuneCookiePoints,
                                                                           happySb.toString(),
                                                                           sadSb.toString());

                                    // set font for Markov text output
                                    Font font = new Font("Sans Serif", Font.ITALIC, 16);
                                    StdDraw.setFont(font);

                                    // generate Markov text, edit it, adn output it to the screen
                                    int k = 7;
                                    int t = 253;
                                    String uneditedText = MarkovModel.markovTextReturn(text, k, t);
                                    String editedText = MarkovModel.fortuneTextEdit(uneditedText);
                                    String[] outputText = MarkovModel.textEditor(editedText);
                                    double OUTPUT_TEXT_X = 0.5;
                                    double OUTPUT_TEXT_Y = 0.5;
                                    for (int i = 0; i < outputText.length; i++) {
                                        StdDraw.text(OUTPUT_TEXT_X, OUTPUT_TEXT_Y, outputText[i]);
                                        OUTPUT_TEXT_Y -= 0.05;
                                    }

                                    // reset font and pen color
                                    StdDraw.setPenColor(Color.BLACK);
                                    StdDraw.setFont();
                                    break;
                                }
                                // if back button clicked, go to next question
                                if (backButton.withinBoundaries()) {
                                    break;
                                }
                            }
                            if (buyButton.withinBoundaries()) break;
                            if (backButton.withinBoundaries()) break;
                        }
                        else if (nextButton.withinBoundaries())
                            break;

                    }
                }

            }
        }


    }
}
