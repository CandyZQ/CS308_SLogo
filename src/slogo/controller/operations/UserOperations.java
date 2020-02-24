package slogo.controller.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserOperations {

    public Map<String, Double> UserVars = new HashMap<>();

    public double makeSet(String variable, double expr) {
        UserVars.put(variable,expr);
        return expr;
    }

    public List<String> repeat(int expr, String commands) {
        List<String> listCommands = new ArrayList<>();
        for (int i = 0; i < expr; i++) {
            listCommands.add(commands);
        }
        return listCommands;
    }

    // public double doTimes() { }

    // public double for_loop(String variable, int start, int end, int increment, String commands) { }

    public String if_statement(int expr, String commands) {
        if (expr != 0) {
            return commands;
        } else {
            return "";
        }
    }

    public String if_else(int expr, String truecommands, String falsecommands) {
        if (expr != 0) {
            return truecommands;
        } else {
            return falsecommands;
        }
    }

    //public String to(String commandName, String variables, String commands) { }
}
