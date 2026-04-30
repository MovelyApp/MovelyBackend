package br.movely.movelyapp.model.enums;

public enum SleepQuality {
    PESSIMO(-20),
    RUIM(-10),
    OK(0),
    BOM(10),
    EXCELENTE(20);

    private final int bonus;

    SleepQuality(int bonus) {
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }
}
