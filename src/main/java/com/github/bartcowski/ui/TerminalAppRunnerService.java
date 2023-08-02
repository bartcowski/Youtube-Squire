package com.github.bartcowski.ui;

import com.github.bartcowski.infrastructure.AppRunnerService;
import com.github.bartcowski.infrastructure.authorization.server.OAuthCallbackServer;
import com.github.bartcowski.infrastructure.viewadapters.CommentServiceViewAdapter;
import com.github.bartcowski.infrastructure.viewadapters.CommentViewDTO;
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
                    1. Just Do It!
                    0. EXIT
                    """);
            System.out.print("> ");
            int userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1: {
                    justDoIt();
                    break;
                }
                case 0: {
                    OAuthCallbackServer.stop(); //TODO: should it be accessed globally? (make non static?)
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

    private void justDoIt() {
        System.out.print("Video ID: ");
        String videoId = scanner.next();
        List<CommentViewDTO> result = commentAdapter.findComments(videoId);
        result.forEach(commentViewDTO -> System.out.println(commentViewDTO.asViewFormattedString()));
    }
}
