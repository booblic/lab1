class Binary extends Calculator {
    private String term;
    private int result = 0;

    public Binary(String str) {
        term = str;
    }

    public void start() {

    }

    public int parse(String str) {
        System.out.print("Result of" + term + "is: ");
        return result;
    }
}