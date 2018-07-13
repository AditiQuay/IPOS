/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package quay.com.ipos.compliance.data.local;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import quay.com.ipos.compliance.data.local.entity.BusinessPlaceEntity;
import quay.com.ipos.compliance.data.local.entity.TaskSchedulerEntity;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    private static final String[] FIRST = new String[]{
            "Special edition", "New", "Cheap", "Quality", "Used"};
    private static final String[] SECOND = new String[]{
            "Three-headed Monkey", "Rubber Chicken", "Pint of Grog", "Monocle"};
    private static final String[] DESCRIPTION = new String[]{
            "is finally here", "is recommended by Stan S. Stanman",
            "is the best sold product on Mêlée Island", "is \uD83D\uDCAF", "is ❤️", "is fine"};
    private static final String[] COMMENTS = new String[]{
            "Comment 1", "Comment 2", "Comment 3", "Comment 4", "Comment 5", "Comment 6"};

    public static List<BusinessPlaceEntity> generateProducts() {
        List<BusinessPlaceEntity> products = new ArrayList<>(FIRST.length * SECOND.length);
        Random rnd = new Random();
        for (int i = 0; i < FIRST.length; i++) {
            for (int j = 0; j < SECOND.length; j++) {
                BusinessPlaceEntity placeEntity = new BusinessPlaceEntity();
                placeEntity.name = FIRST[i] + " " + SECOND[j];
                placeEntity.id = FIRST.length * i + j + 1;
                products.add(placeEntity);
            }
        }
        return products;
    }

    public static List<TaskSchedulerEntity> generateCommentsForProducts(
            final List<BusinessPlaceEntity> products) {
        List<TaskSchedulerEntity> schedulerEntityArrayList = new ArrayList<>();
        Random rnd = new Random();

        for (BusinessPlaceEntity businessPlaceEntity : products) {
            int commentsNumber = rnd.nextInt(5) + 1;
            for (int i = 0; i < commentsNumber; i++) {
                TaskSchedulerEntity taskSchedulerEntity = new TaskSchedulerEntity();
                taskSchedulerEntity.placeId = businessPlaceEntity.id;
                schedulerEntityArrayList.add(taskSchedulerEntity);
            }
        }

        return schedulerEntityArrayList;
    }
}
