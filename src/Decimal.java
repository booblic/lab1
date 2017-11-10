import java.util.Scanner;

class Decimal extends Calculator {
    private String term;

    private StringBuilder termstr = new StringBuilder();
    private int closeBracket, openBracket;

    public Decimal(String str) {
        term = str;
    }

    public void start() {
        System.out.println("Result of term " + term + " is: " + parse(formater(term)));
    }

    private String formater(String str) {
        if (str == null) return null;
        //str = str.replaceAll(" ", "");
        //return String.join(" ", str.split("(?<=\\D)|(?=\\D)"));
        return str.replaceAll(" ", "");
    }

    public int parse(String input) {

        int a, b;
        Integer c;

        termstr.delete(0, termstr.length());
        termstr.append(input);

        for (int i = 0; i < termstr.length(); i++)
        {
            if(termstr.charAt(i)=='(') {
                openBracket=i;
            }
            if(termstr.charAt(i)==')') {
                closeBracket=i;
                String newtermstr=new String();
                newtermstr=termstr.substring(openBracket + 1, closeBracket);
                System.out.println("Выражение в скобках: " + newtermstr);
                termstr.delete(openBracket, closeBracket + 1);
                System.out.println("После удаления выражения в скобках из основной строки: " + termstr);
                StringBuilder mainString = new StringBuilder(termstr);
                System.out.println("Новая строка без выражения в скобках: " + mainString);
                parse(newtermstr);
                mainString.insert(openBracket, termstr.toString());
                System.out.println("Новая строка со вставленной старой вместо скобок: " + mainString);
                termstr.delete(0, termstr.length());
                termstr.insert(0, mainString.toString());
                System.out.println("После расчетов: " + termstr);
                parse(termstr.toString());
            }
        }

        for (int i = 0; i < termstr.length(); i++) {
            if (termstr.charAt(i) == '*') {
                a = Integer.parseInt(String.valueOf(termstr.charAt(i - 1)));
                b = Integer.parseInt(String.valueOf(termstr.charAt(i + 1)));
                System.out.println("Спарсенное выражение: " + a + termstr.charAt(i) + b);
                System.out.println("До удаления: " + termstr);
                termstr.delete(i - 1, i + 2);
                System.out.println("После удаления: " + termstr);
                c = a * b;
                System.out.println("Результат: " + c);
                termstr.insert(i - 1, c.toString());
                System.out.println("Итог: " + termstr);
                break;
            }
            if (termstr.charAt(i) == '/') {
                a = Integer.parseInt(String.valueOf(termstr.charAt(i - 1)));
                b = Integer.parseInt(String.valueOf(termstr.charAt(i + 1)));
                System.out.println("Спарсенное выражение: " + a + termstr.charAt(i) + b);
                System.out.println("До удаления: " + termstr);
                termstr.delete(i - 1, i + 2);
                System.out.println("После удаления: " + termstr);
                c = a / b;
                System.out.println("Результат: " + c);
                termstr.insert(i - 1, c.toString());
                System.out.println("Итог: " + termstr);
                break;
            }
        }

        for (int i = 0; i < termstr.length(); i++) {
            if ((termstr.charAt(i) == '*') || (termstr.charAt(i) == '/')) {
                parse(termstr.toString());
            }
        }

        for (int i = 0; i < termstr.length(); i++) {
            if (termstr.charAt(i) == '+') {
                a = Integer.parseInt(String.valueOf(termstr.charAt(i - 1)));
                b = Integer.parseInt(String.valueOf(termstr.charAt(i + 1)));
                System.out.println("Спарсенное выражение: " + a + termstr.charAt(i) + b);
                System.out.println("До удаления: " + termstr);
                termstr.delete(i - 1, i + 2);
                System.out.println("После удаления: " + termstr);
                c = a + b;
                System.out.println("Результат: " + c);
                termstr.insert(i - 1, c.toString());
                System.out.println("Итог: " + termstr);
                break;
            }
            if (termstr.charAt(i) == '-') {
                a = Integer.parseInt(String.valueOf(termstr.charAt(i - 1)));
                b = Integer.parseInt(String.valueOf(termstr.charAt(i + 1)));
                System.out.println("Спарсенное выражение: " + a + termstr.charAt(i) + b);
                System.out.println("До удаления: " + termstr);
                termstr.delete(i - 1, i + 2);
                System.out.println("После удаления: " + termstr);
                c = a - b;
                System.out.println("Результат: " + c);
                termstr.insert(i - 1, c.toString());
                System.out.println("Итог: " + termstr);
                break;
            }
        }

        for (int i = 0; i < termstr.length(); i++) {
            if ((termstr.charAt(i) == '+') || (termstr.charAt(i) == '-')) {
                parse(termstr.toString());
            }
        }

        /*for (int i = 0; i < cstring.length(); i++) {
            if (cstring.charAt(i) == '+') {
                CharsPos[0] = 0;
                for (int j = 0, k = 1; j < cstring.length() - 1; j++) {
                    if ((cstring.charAt(j) == '+') || (cstring.charAt(j) == '-') ||
                            (cstring.charAt(j) == '*') || (cstring.charAt(j) == '/') ||
                            (cstring.charAt(j) == '(') || (cstring.charAt(j) == ')')) {
                        CharsPos[k] = j + 1;
                        k++;
                    }
                    CharsPos[CharsPos.length - 1] = cstring.length() + 1;
                }
                for (int k = 0; k < CharsPos.length; k++) {

                    if (CharsPos[k] == i + 1) {
                        StartPos = CharsPos[k - 1];
                        FinishPos = CharsPos[k + 1] - 1;
                    }
                }
                a = Integer.parseInt(cstring.substring(StartPos, i));
                System.out.println("Начало: " + a);
                b = Integer.parseInt(cstring.substring(i + 1, FinishPos));
                System.out.println("Конец: " + b);
                cstring.delete(StartPos, FinishPos);
                c = a + b;
                cstring.insert(StartPos, c.toString());
                break;
            }
        }*/



        /*int i, j;
        String s;

        String[] str1 = str.split(" ");
        for (String s1: str1) {



            if (s.equals("(")) {
                System.out.println("Y");
            }
            else System.out.println("N");

        }


        Scanner scanner = new Scanner(str);

        char[] ch = term.toCharArray();
        Character[] characters = new Character[ch.length];

        for (int i = 0; i < ch.length; i++) {
            characters[i] = ch[i];
        }

        List<Character> charlist = new ArrayList<Character>(Arrays.asList(characters));


        for (int i = 0; i < term.length(); i++) {
            if (term.charAt(i) == '+') {
                System.out.print(term.charAt(i - 1));
                System.out.print(term.charAt(i));
                System.out.print(term.charAt(i + 1) + "  ");
            }
        }

        System.out.print("Result of term " + str + " is: ");*/
        return Integer.parseInt(termstr.toString());
    }
}