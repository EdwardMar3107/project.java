package by.ezer.test;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailTestRunner implements CommandLineRunner {

    private final EmailTestService emailTestService;

    @Override
    public void run(String... args) throws Exception {
        emailTestService.sendTestEmail();
    }
}
