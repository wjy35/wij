package com.wjy35.wij.run.judge.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class JudgeResult {
    private final String actual;
    private final String expected;

    public JudgeResult(String actual, String expected) {
        this.actual = actual;
        this.expected = expected;
    }

    public boolean isAccepted() {
        if(actual.isEmpty()) return false;
        try {
            BufferedReader expectedBr = new BufferedReader(new StringReader(expected));
            BufferedReader actualBr = new BufferedReader(new StringReader(actual));
            String expectedLine = null;
            String actualLine = null;

            while(((expectedLine = expectedBr.readLine()) != null) & ((actualLine = actualBr.readLine()) != null)){
                if(!expectedLine.stripTrailing().equals(actualLine.stripTrailing())) return  false;
            }

            while(expectedLine!=null && expectedLine.isEmpty()) expectedLine = expectedBr.readLine();
            while(actualLine!=null && actualLine.isEmpty()) actualLine = actualBr.readLine();

            if(expectedLine!=null) return false;
            if(actualLine!=null) return false;
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
