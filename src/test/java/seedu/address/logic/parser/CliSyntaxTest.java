package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.fail;

public class CliSyntaxTest {

    @Test
    public void assertNoPostfix() {
        //@@author nighoggDatatype-reused
        //Reused from https://stackoverflow.com/a/58150078/11358676 with minor modifications
        List<String> noPostfixList = Arrays.stream(CliSyntax.class.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> !Modifier.isTransient(field.getModifiers()))
                .map(field -> {
                    try {
                        Prefix prefix =  (Prefix) field.get(Prefix.class);
                        return prefix.getPrefix();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        //@@author
        int N = noPostfixList.size();
        for (int i = 0; i < N-1; i++) {
            for(int j = i+1; j < N; j++) {
                String shorterPrefix = noPostfixList.get(i);
                String longerPrefix = noPostfixList.get(j);
                if (shorterPrefix.length() > longerPrefix.length()) {
                    String temp = shorterPrefix;
                    shorterPrefix = longerPrefix;
                    longerPrefix = temp;
                }
                if (longerPrefix.contains(shorterPrefix)) {
                    fail();
                }
            }
        }
    }
}
