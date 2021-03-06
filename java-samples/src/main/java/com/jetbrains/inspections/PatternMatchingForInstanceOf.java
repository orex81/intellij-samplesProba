package com.jetbrains.inspections;

import com.jetbrains.inspections.entities.Employee;
import com.jetbrains.inspections.entities.Person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * <p>Pattern Matching for instanceof</p>
 * <p>
 * #PreviewFeature First Preview #JDK14<br/>
 * #PreviewFeature Second Preview #JDK15<br/>
 * #StandardFeature #JDK16<br/>
 */
@SuppressWarnings("unused")
public class PatternMatchingForInstanceOf {

    // Remember you can run these sorts of inspections over your whole application code
    public void inspectionToConvertInstanceof(Person person) {
        if (person instanceof Employee) {
            Employee employee = (Employee) person;
            if (employee.isBasedInOffice()) {
                employee.workFromHome();
            }
        }

        if (person instanceof Employee) {
            // 2021.1 / #JDK16 this can be replaced with a pattern variable
            Employee employee = (Employee) person;
            System.out.println(employee);
            employee = new Employee();
            if (employee.isBasedInOffice()) {
                employee.workFromHome();
            }
        }
    }

    public void extractVariableOrInline(Person person) {
        if (person instanceof Employee) {
            if (((Employee) person).isBasedInOffice()) {
                System.out.println("Works from the office");
            }
        }
    }

    public List<Node> combinesWithOtherSimplificationInspections() {
        final ArrayList<Node> nodes = getNodes();
        for (Iterator<Node> iterator = nodes.iterator(); iterator.hasNext(); ) {
            Node node = iterator.next();
            if (node instanceof LetterNode) {
                LetterNode letterNode = (LetterNode) node;
                if (letterNode.isLatin()) {
                    if (isLetterTrueFont(letterNode.nodeValue())) {
                        iterator.remove();
                    }
                }
            }
        }
        return nodes;
    }

    public void examplesOfUpdatesToPatternMatchingInJava16(Person person) {
        if (person instanceof Employee employee) {
            // in #JDK16 you can change this pattern variable - IntelliJ IDEA marks mutated variables with underline
            employee = new Employee();

            if (employee.isBasedInOffice()) {
                employee.workFromHome();
            }
        }
        // in #JDK16 you will need to mark this as final if you don't want it to change
        // if a variable is final or effectively final, IntelliJ IDEA does not underline it
        if (person instanceof final Employee employee) {
            if (employee.isBasedInOffice()) {
                employee.workFromHome();
            }
        }
    }

    void exampleThatNeedsManualHelp(Object x) {
        if (x instanceof Integer && (Integer) x > 0) {
            Integer integer = (Integer) x;
            System.out.println(integer + 1);
        }
    }

    //<editor-fold desc="Helper methods">
    private boolean isLetterTrueFont(Object nodeValue) {
        return false;
    }

    private ArrayList<Node> getNodes() {
        return new ArrayList<>();
    }
    //</editor-fold>

}

@SuppressWarnings("unused")
class LineItem {
    private String description;
    private double price;

    @Override
    public boolean equals(Object o) {
        if (o instanceof LineItem) {
            LineItem other = (LineItem) o;
            if (description.equals(other.description) && price == other.price) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, price);
    }
}

class Node {
    public Object nodeValue() {
        return null;
    }
}

class LetterNode extends Node {
    public boolean isLatin() {
        return false;
    }
}

