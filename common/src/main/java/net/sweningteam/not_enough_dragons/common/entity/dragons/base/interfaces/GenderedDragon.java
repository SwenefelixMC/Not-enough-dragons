package net.sweningteam.not_enough_dragons.common.entity.dragons.base.interfaces;

public interface GenderedDragon {
    enum genders{
        MALE,
        FEMALE
    }

    boolean isMale();

    void SetGender(genders gender);

    genders getGender();
}
