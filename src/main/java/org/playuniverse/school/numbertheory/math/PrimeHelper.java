package org.playuniverse.school.numbertheory.math;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class PrimeHelper {

    public static final PrimeHelper INSTANCE = new PrimeHelper();

    private final ArrayList<Long> primes = new ArrayList<>();

    private PrimeHelper() {
        primes.add(2L);
    }
    
    public String toFractionString(long value) {
        return toString(value, findFractions(value));
    }

    public String toString(long value, Map<Long, Long> map) {
        if (map.isEmpty()) {
            return value + " = " + value;
        }
        StringBuilder builder = new StringBuilder().append(value).append(" = ");
        for (Entry<Long, Long> entry : map.entrySet()) {
            builder.append(entry.getKey());
            if (entry.getValue() == 1) {
                builder.append(" * ");
                continue;
            }
            builder.append('^').append(entry.getValue()).append(" * ");
        }
        return builder.substring(0, builder.length() - 3);
    }

    public Map<Long, Long> findFractions(long value) {
        LinkedHashMap<Long, Long> map = new LinkedHashMap<>();
        if (value == 0 || value == 1) {
            return map;
        }
        long fraction;
        long current = value;
        while ((fraction = findFraction(current)) <= current) {
            if (fraction == 1) {
                return map;
            }
            current /= fraction;
            if (map.containsKey(fraction)) {
                map.put(fraction, map.get(fraction) + 1);
            } else {
                map.put(fraction, 1L);
            }
            if (current == 1) {
                return map;
            }
        }
        return map;
    }

    public boolean isPrime(long value) {
        if (primes.contains(value)) {
            return true;
        }
        long current;
        while ((current = findNextPrime()) < value) {
        }
        return current == value;
    }

    private long findFraction(long input) {
        for (long prime : primes) {
            if (input % prime == 0) {
                return prime;
            }
        }
        long current;
        while ((current = findNextPrime()) < input) {
            if (input % current == 0) {
                return current;
            }
        }
        return isPrime(input) ? input : 1;
    }

    private long findNextPrime() {
        long current = primes.get(primes.size() - 1) + 1;
        while (!testPrime(current)) {
            current++;
        }
        primes.add(current);
        return current;
    }

    private boolean testPrime(long value) {
        long approx = FastMath.Q_rsqrt(value);
        int size = primes.size();
        for (int index = 0; index < size; index++) {
            long prime = primes.get(index);
            if (prime > approx) {
                return true;
            }
            if (value % prime == 0) {
                return false;
            }
        }
        return true;
    }

}
