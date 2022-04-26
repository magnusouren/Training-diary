package trainingDiary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Exercise {

    private String name;
    private int weight;
    private List<Integer> reps = new ArrayList<>();

    public Exercise() {
    }

    /**
     * Constructor
     * 
     * @param name   String name of exercise
     * @param weight int weight of exercise
     * @param reps   int... reps
     */
    public Exercise(String name, int weight, int... reps) {
        setName(name);
        setWeight(weight);
        for (int i : reps) {
            addRep(i);
        }
    }

    /**
     * Sets name of exercise
     * 
     * @param name String name of exercise
     * @throws IllegalArgumentException if name has illegal format
     */
    public void setName(String name) throws IllegalArgumentException {
        if (!name.matches("[a-zA-Z]{2,}"))
            throw new IllegalArgumentException("Illegal name, must contain two or more letters\n");
        this.name = name;
    }

    /**
     * Sets weight of exercise
     * 
     * @param weigth int weight of exercise
     * @throws IllegalArgumentExceptioin if weight is not in the
     *                                   interval [0,300]
     * 
     */
    public void setWeight(int weigth) throws IllegalArgumentException {
        if (weigth < 0)
            throw new IllegalArgumentException("Illegal weight, must be greater than 0\n");
        if (weigth > 300)
            throw new IllegalArgumentException("Illegal weight, must be less than 300\n");
        this.weight = weigth;
    }

    /**
     * Validates value on rep and adds to reps if valid
     * 
     * @param rep int value for amount of repetisions
     * @throws IllegalArgumentException if rep is greater than 1000 or less or equal
     *                                  than 0
     */
    public void addRep(int rep) throws IllegalArgumentException {
        if (rep <= 0)
            throw new IllegalArgumentException("Illegal set, must be greater than 0\n");
        else if (rep > 1000)
            throw new IllegalArgumentException("Illegal set, cannot be greater than 1000\n");
        else
            reps.add(rep);
    }

    /**
     * @return String name of exercise
     */
    public String getName() {
        return name;
    }

    /**
     * @return List<Integer> of reps
     */
    public List<Integer> getReps() {
        return new ArrayList<>(reps);
    }

    /**
     * @return int weight of exercise
     */
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        String res = name + ": ";

        if (reps.stream().distinct().toList().size() == 1) {
            res += reps.size() + " x " + reps.get(0);
        } else {
            res += reps.stream()
                    .map(r -> String.valueOf(r))
                    .collect(Collectors.joining(", "));
        }
        if (weight != 0)
            res += " - " + weight + "kg";

        return res;
    }

}
