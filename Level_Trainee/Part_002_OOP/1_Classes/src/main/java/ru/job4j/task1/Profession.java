package ru.job4j.task1;

/**
 * Class Profession describes a simple template of a profession.
 *
 * @author Alex Proshak (olexandr_proshak@ukr.net)
 */
public abstract class Profession extends Human {

    /**
     * A specialization of humans being.
     */
    private String specialization;

    /**
     * A high school was graduated by humans being.
     */
    private String graduatedSchool;

    /**
     * An experience of humans being.
     */
    private int experience;

    /**
     * A wage for work accepted by humans being.
     */
    private float salary = 0.0f;

    /**
     * A simple constructor.
     */
    protected Profession() {
    }

    /**
     * A constructor with parameters.
     * @param name for super class.
     * @param specialization for super class.
     * @param graduatedSchool for super class.
     * @param experience for super class.
     */
    protected Profession(String name, String specialization, String graduatedSchool, int experience) {
        super(name);
        this.specialization = specialization;
        this.graduatedSchool = graduatedSchool;
        this.experience = experience;
    }

    /**
     * Increasing a field of a salary.
     * @param amount of MONEY per month for work.
     * @param month was paid.
     */
    protected void earnedSalary(float amount, String month) {
        this.salary = amount;
    }

    /**
     * A setter for salary's field.
     * @param salary wage for work.
     */
    public void setSalary(float salary) {
        this.salary = salary;
    }

    /**
     * A getter for salary's field.
     * @return amount of MONEY.
     */
    public float getSalary() {
        return salary;
    }
}
