class Binary extends Calculator {
    private String term;
    private StringBuilder result;

    public Binary(String str) {
        term = str;
    }

    public void start() {

    }

    public StringBuilder calculation(String str) {
        System.out.print("Result of" + term + "is: ");
        return result;
    }
}