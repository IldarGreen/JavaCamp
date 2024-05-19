package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) throws IllegalNumberException {
        boolean result = true;
        if (number > 1) {
            for (int i = 2; i * i <= number && i <= Math.sqrt(Integer.MAX_VALUE); i++) {
                if (number % i == 0) {
                    result = false;
                    break;
                }
            }
        } else {
            throw new IllegalNumberException("The number must be greater than 1");
        }
        return result;
    }

    public int digitsSum(int number) {
        number = (number < 0) ? -number : number;
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number = number / 10;
        }
        return sum;
    }
}
