package com.github.bartcowski.ui;

import com.github.bartcowski.infrastructure.AppRunnerService;
import com.github.bartcowski.infrastructure.viewadapters.CommentServiceViewAdapter;
import com.github.bartcowski.infrastructure.viewadapters.ViewEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
@AllArgsConstructor
class TerminalAppRunnerService implements AppRunnerService {

    private final Scanner scanner = new Scanner(System.in);

    private final CommentServiceViewAdapter commentAdapter;

    @Override
    public void run() {
        boolean run = true;

        while (run) {
            System.out.print("""
                    1. all comments
                    2. comments containing phrase
                    3. random comment containing phrase
                    4. random comment containing phrase with deduplicated authors
                    5. comments containing phrase COUNT
                    6. top liked comments
                    0. EXIT
                    """);
            System.out.print("> ");
            int userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1: {
                    String videoId = askForVideoId();
                    printList(commentAdapter.findAllComments(videoId));
                    break;
                }
                case 2: {
                    String videoId = askForVideoId();
                    String phrase = askForPhrase();
                    printList(commentAdapter.findCommentsWithPhrase(videoId, phrase));
                    break;
                }
                case 3: {
                    String videoId = askForVideoId();
                    String phrase = askForPhrase();
                    printSingle(commentAdapter.findRandomCommentWithPhrase(videoId, phrase));
                    break;
                }
                case 4: {
                    String videoId = askForVideoId();
                    String phrase = askForPhrase();
                    printSingle(commentAdapter.findRandomCommentWithPhraseAndDeduplicatedAuthors(videoId, phrase));
                    break;
                }
                case 5: {
                    String videoId = askForVideoId();
                    String phrase = askForPhrase();
                    printNumber(commentAdapter.countCommentsWithPhrase(videoId, phrase));
                    break;
                }
                case 6: {
                    String videoId = askForVideoId();
                    int count = askForCount();
                    printList(commentAdapter.findTopLikedComments(videoId, count));
                    break;
                }
                case 0: {
                    run = false;
                    System.out.println("Bye!");
                    break;
                }
                default: {
                    System.out.print("Desired feature is not available :(");
                }
            }
        }
    }

    private String askForVideoId() {
        System.out.print("Video ID: ");
        return scanner.next();
    }

    private String askForPhrase() {
        System.out.print("Phrase: ");
        return scanner.next();
    }

    private int askForCount() {
        System.out.print("Count: ");
        return Integer.parseInt(scanner.next());
    }

    private void printList(List<? extends ViewEntity> list) {
        list.forEach(single -> System.out.println(single.asViewFormattedString()));
    }

    private <T extends ViewEntity> void printSingle(T single) {
        System.out.println(single.asViewFormattedString());
    }

    private <T extends Number> void printNumber(T number) {
        System.out.println("Resulting count: " + number);
    }
}
