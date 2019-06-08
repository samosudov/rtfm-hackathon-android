/*
 * Copyright (C) 2017 The Android Open Source Project
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

package work.samosudov.rtfm.persistence.txs;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

/**
 * Immutable model class for a Tx
 */
@Entity(tableName = "tx")
public class Tx {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "value")
    private Double value;

    @ColumnInfo(name = "time")
    private Long time;

    @Ignore
    public Tx(Double value, Long time) {
        id = UUID.randomUUID().toString();
        this.value = value;
        this.time = time;
    }

    public Tx(@NonNull String id, Double value, Long time) {
        this.id = id;
        this.value = value;
        this.time = time;
    }

    public static Tx parseTx(String str) {
        return new Tx(str, 0.0, System.currentTimeMillis());
    }

    @NonNull
    public String getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public Long getTime() {
        return time;
    }
}
