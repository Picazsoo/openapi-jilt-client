package cz.metlicka.client.utils;

import cz.metlicka.client.model.AddressBuilder;
import cz.metlicka.client.model.UserCreateRequest;
import cz.metlicka.client.model.UserCreateRequestBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUserGenerator {

    private static final List<String> firstNames = List.of("Caliyah", "Mireya", "Eidan", "Ollie", "Zooey", "Holdyn", "Alizee", "Auria", "Stormi", "Nechama");
    private static final List<String> lastNames = List.of("Mounts", "Shim", "Bolanos", "Branham", "Herrmann", "Lund", "Orosco", "Brill", "Earle", "George");
    private static final List<String> streets = List.of("Milne Field", "Lammas Furlong", "Carnegie Drive", "Rushden Croft", "Lammas Oval", "Christchurch Gait", "St Marys Glas", "Albert Hall Place", "Albert Drive", "Belyars Lane");
    private static final List<String> cities = List.of("Milan", "Luanda", "Bangalore", "Rio de Janeiro", "Moscow", "São Paulo", "Kuala Lumpur", "Dhaka", "Sofia", "Kyiv");
    private static final List<String> states = List.of("Utah", "Hawaii", "Colorado", "Kansas", "Nebraska", "Vermont", "Michigan", "Alabama", "Louisiana", "Montana");
    private static final List<String> zips = List.of("10581", "15150-7897", "13888", "53675-1889", "33820", "93554-8871", "41555-2148", "53307-2352", "38338", "51647");

    public static UserCreateRequest prepareRandomUser() {
        return UserCreateRequestBuilder.userCreateRequest()
                .address(AddressBuilder.address()
                        .street(getRandomFromList(streets))
                        .city(getRandomFromList(cities))
                        .state(getRandomFromList(states))
                        .zip(getRandomFromList(zips))
                        .build()
                )
                .firstName(getRandomFromList(firstNames))
                .lastName(getRandomFromList(lastNames))
                .build();
    }

    private static String getRandomFromList(List<String> list) {
        return list.get(ThreadLocalRandom.current().nextInt(0, firstNames.size()));
    }
}
