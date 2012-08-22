package com.tgid.tri.race

/**
 */
enum RaceCategoryType implements Serializable {

    Olympic('Olympic'),
    Sprint('Sprint'),
    Marathon('Marathon'),
    HalfMarathon('Half Marathon'),
    Ironman('Ironman'),
    HalfIronman('Half Ironman'),
    OneMile('1 Mile'),
    ThreeMile('3 Mile'),
    FiveMile('5 Mile'),
    TenMile('10 Mile'),
    TwentyMile('20 Mile'),
    OneKilometer('1K'),
    FiveKilometer('5K'),
    EightKilometer('8K'),
    TenKilometer('10K'),
    FifteenKilometer('15K'),
    TwentyFiveKilometer('25K'),
    Criterium('Criterium'),
    Road('Road'),
    TimeTrial('Time Trial'),
    Training('Training'),
    Unknown('?')

    String raceCategoryType

    RaceCategoryType(String raceCategoryType) {
        this.raceCategoryType = raceCategoryType
    }

    static RaceCategoryType getRaceCategoryType(String value) {
        RaceCategoryType.values().find { it.raceCategoryType == value }
    }

    @Override
    String toString() {
        this.raceCategoryType
    }
}