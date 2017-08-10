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

package ch.ccapps.android.zeneggen.model.db;

import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import ch.ccapps.android.zeneggen.model.AppUser;
import ch.ccapps.android.zeneggen.model.db.entity.Event;
import ch.ccapps.android.zeneggen.model.db.dao.AppUserDao;
import ch.ccapps.android.zeneggen.model.db.dao.EventDao;
import ch.ccapps.android.zeneggen.model.db.dao.EventParticipationDao;
import ch.ccapps.android.zeneggen.model.db.dao.ParticipantDao;

@Database(entities = {Event.class, AppUser.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract EventDao eventModel();
    public abstract AppUserDao appUserModel();
    public abstract ParticipantDao participantDao();
    public abstract EventParticipationDao eventParticipantDao();

    public static AppDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,"zeneggendb")
                    // To simplify the codelab, allow queries on the main thread.
                    // Don't do this on a real app! See PersistenceBasicSample for an example.
                    //.allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}