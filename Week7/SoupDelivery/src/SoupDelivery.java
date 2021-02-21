import java.util.*;

public class SoupDelivery {
    static int n, m;
    static HashMap<Integer, Integer> rentCost;//rent cost for each facility
    static HashMap<Integer, HashMap<Integer, Integer>> deliveryCost;//the delivery cost from a facility to all customers
    static long totalSum;
    static HashMap<Integer, HashSet<Integer>> serves;//what customers each facility serves
    static double factor;
    static HashSet<Integer> used;

    static long remove(int facility) {
        long change = 0;

        long addSum = 0;
        long subtractSum = rentCost.get(facility);

        //get the customers that this facility serves and find other facilities to serve them
        HashSet<Integer> customers = new HashSet<>();
        customers.addAll(serves.get(facility));

        //pair: (active facility, the new customers that this facility serves if *facility* is removed)
        HashMap<Integer, HashSet> modifications = new HashMap<>();

        Iterator<Integer> itUsed = used.iterator();
        while (itUsed.hasNext()) {
            int f = itUsed.next();
            if (f != facility) {
                modifications.put(f, new HashSet());
            }
        }

        Iterator<Integer> it = customers.iterator();
        while (it.hasNext()) {
            int c = it.next();

            long min = Long.MAX_VALUE;
            int servedBy = -1;

            Iterator<Integer> iteratorUsed = used.iterator();
            while (iteratorUsed.hasNext()) {
                int f = iteratorUsed.next();
                if (f != facility) {
                    long newCost = deliveryCost.get(f).get(c);
                    if (newCost < min) {
                        min = newCost;
                        servedBy = f;
                    }
                }
            }
            if (servedBy != -1) {
                modifications.get(servedBy).add(c);
                addSum += min;
                subtractSum += deliveryCost.get(facility).get(c);
            } else {
                //impossible to remove this facility
                return change;
            }
        }

        long better = totalSum - subtractSum + addSum;
        if (totalSum - better >= factor) {
            change = totalSum - better;

            totalSum = better;
            used.remove(facility);
            serves.get(facility).clear();

            //iterate through the modifications
            Iterator iterator = modifications.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                int f = (int) pair.getKey();
                serves.get(f).addAll((HashSet) pair.getValue());
            }
        }
        return change;
    }

    static long add(int facility) {
        //go through all the customers and see if we can find better delivery costs
        long addSum = rentCost.get(facility);
        long subtractSum = 0;

        long change = 0;

        HashMap<Integer, HashSet> modifications = new HashMap<>();

        //iterate through all active facilities, see if we can obtain a smaller delivery cost
        for (int i = 1; i <= n; i++) {
            if (used.contains(i)) {
                HashSet<Integer> oldCustomers = new HashSet<>();

                HashSet<Integer> customers = new HashSet<>();
                customers.addAll(serves.get(i));
                Iterator<Integer> it = customers.iterator();

                while (it.hasNext()) {
                    int c = it.next();
                    int oldCost = deliveryCost.get(i).get(c);
                    int newCost = deliveryCost.get(facility).get(c);
                    if (oldCost > newCost) {
                        addSum += newCost;
                        subtractSum += oldCost;
                        oldCustomers.add(c);
                    }
                }
                //see if a facility serves now 0 customers
                if (oldCustomers.size() == customers.size()) {
                    subtractSum += rentCost.get(i);
                    //not used anymore -> add -1 at the end of oldCustomers -> dummy element
                    oldCustomers.add(-1);
                }
                modifications.put(i, oldCustomers);
            }
        }

        long better = totalSum - subtractSum + addSum;

        if (totalSum - better >= factor) {
            change = totalSum - better;

            totalSum = better;
            used.add(facility);

            //iterate through the modifications
            Iterator it = modifications.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                int f = (int) pair.getKey();
                HashSet<Integer> customers = new HashSet<>();
                customers.addAll((HashSet) pair.getValue());
                if (customers.contains(-1)) {
                    customers.remove(-1);
                    used.remove(f);
                }
                serves.get(f).removeAll(customers);
                serves.get(facility).addAll(customers);
            }
        }
        return change;
    }

    static long replace(int facility) {
        long change = 0;

        long addSum = 0;
        long subtractSum = rentCost.get(facility);

        //iterate through all the customers served by this facility
        HashSet<Integer> customers = new HashSet<>();
        customers.addAll(serves.get(facility));
        Iterator<Integer> it = customers.iterator();
        while (it.hasNext()) {
            int c = it.next();
            subtractSum += deliveryCost.get(facility).get(c);
        }

        long minReplacement = Long.MAX_VALUE;
        int replacement = -1;

        for (int i = 1; i <= n; i++) {
            if (!used.contains(i)) {
                long tmp = rentCost.get(i);
                Iterator<Integer> iterator = customers.iterator();
                while (iterator.hasNext()) {
                    int c = iterator.next();
                    tmp += deliveryCost.get(i).get(c);
                }
                if (tmp < minReplacement) {
                    replacement = i;
                    minReplacement = tmp;
                }
            }
        }
        if (replacement != -1) {
            addSum = minReplacement;
            long better = totalSum - subtractSum + addSum;
            if (totalSum - better >= factor) {
                change = totalSum - better;
                totalSum = better;
                used.remove(facility);
                used.add(replacement);

                serves.get(replacement).addAll(customers);
                serves.get(facility).clear();
            }
        }
        return change;
    }

    static void solve() {
        long change = 1;//default value to go to the loop
        int last = 1;
        long tmp;

        while (change >= factor) {
            change = 0;
            for (int i = last; i <= n; i++) {
                if (used.contains(i)) {
                    tmp = remove(i);
                    if (tmp > 0) {
                        change = tmp;
                        last = i;
                    } else {
                        tmp = replace(i);
                        if (tmp > 0) {
                            change = tmp;
                            last = i;
                        }
                    }
                } else if (!used.contains(i)) {
                    tmp = add(i);
                    if (tmp > 0) {
                        change = tmp;
                        last = i;
                    }
                }
            }
            for (int i = 1; i <= last - 1; i++) {
                if (used.contains(i)) {
                    tmp = remove(i);
                    if (tmp > 0) {
                        change = tmp;
                        last = i;
                    } else {
                        tmp = replace(i);
                        if (tmp > 0) {
                            change = tmp;
                            last = i;
                        }
                    }
                } else if (!used.contains(i)) {
                    tmp = add(i);
                    if (tmp > 0) {
                        change = tmp;
                        last = i;
                    }
                }
            }

            last++;
            if (last == n + 1) {
                last = 1;
            }
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        //start solution: all customers are served by the first facility

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();//locations
            m = s.nextInt();//customers

            rentCost = new HashMap<>();
            deliveryCost = new HashMap<>();
            serves = new HashMap<>();
            used = new HashSet<>();

            long sumDelivery = 0;
            long sumLocation = 0;

            for (int i = 1; i <= n; i++) {
                int c = s.nextInt();
                rentCost.put(i, c);
                deliveryCost.put(i, new HashMap<>());
                serves.put(i, new HashSet());
            }

            used.add(1);
            sumLocation += rentCost.get(1);

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    int d = s.nextInt();
                    deliveryCost.get(i).put(j, d);

                    if (i == 1) {
                        serves.get(1).add(j);
                        sumDelivery += d;
                    }
                }
            }

            factor = 1 - 1 / (2 * Math.pow((n + m), 2));
            totalSum = sumLocation + sumDelivery;

            solve();

            System.out.println("Case " + "#" + test + ": " + totalSum);
            for (int i = 1; i <= n; i++) {
                if (used.contains(i)) {
                    System.out.print(i + " ");
                    HashSet<Integer> set = new HashSet<>();
                    set.addAll(serves.get(i));
                    Iterator<Integer> it = set.iterator();
                    while (it.hasNext()) {
                        int nr = it.next();
                        System.out.print(nr + " ");
                    }
                    System.out.println();
                }
            }
        }
        s.close();
    }
}
