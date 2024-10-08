package ru.progwards.java2.app.consult1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum DataBase {
    INSTANCE;

    public final static String DB_PATH = "C:/consult_db/";

    public Users users = new Users();
    public Consultations consultations = new Consultations();
    public Schedule schedule = new Schedule();
    public Settings settings = new Settings();

    // таблица пользователи
    public static class Users extends AbstractDbTable<String, Users.User> {
        public static class User {
            public final String login;
            public final String password;
            public final String name;
            public final boolean is_mentor;
            public final String image;

            public User(@JsonProperty("login") String login,
                        @JsonProperty("password") String password,
                        @JsonProperty("name") String name,
                        @JsonProperty("is_mentor") boolean is_mentor,
                        @JsonProperty("image") String image) {
                this.login = login;
                this.password = password;
                this.name = name;
                this.is_mentor = is_mentor;
                this.image = image;
            }

            @Override
            public String toString() {
                return login;
            }

            //getters
            public String getLogin() {
                return login;
            }

            public String getPassword() {
                return password;
            }

            public String getName() {
                return name;
            }

            public boolean isIs_mentor() {
                return is_mentor;
            }

            public String getImage() {
                return image;
            }
        }

        private Users() {
            super(new TypeToken<ArrayList<User>>() {
            }.getType());
        }

        @Override
        public String getTableName() {
            return "users.json";
        }

        @Override
        public String getKey(User elem) {
            return elem.login;
        }
    }

    // таблица консультации
    public static class Consultations extends AbstractDbTable<Consultations.Key, Consultations.Consultation> {
        public static class Consultation {
            public final String mentor;
            public final long start;
            public final long duration;
            public final String student;
            public final String comment;

            public Consultation(@JsonProperty("mentor") String mentor,
                                @JsonProperty("start") long start,
                                @JsonProperty("duration") long duration,
                                @JsonProperty("student") String student,
                                @JsonProperty("comment") String comment) {
                this.mentor = mentor;
                this.start = start;
                this.duration = duration;
                this.student = student;
                this.comment = comment;
            }

            //getters
            public String getMentor() {
                return mentor;
            }

            public long getStart() {
                return start;
            }

            public long getDuration() {
                return duration;
            }

            public String getStudent() {
                return student;
            }

            public String getComment() {
                return comment;
            }
        }

        public static class Key {
            public final String mentor;
            public final long start;

            public Key(String mentor, long start) {
                this.mentor = mentor;
                this.start = start;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Key key = (Key) o;
                return start == key.start &&
                        mentor.equals(key.mentor);
            }

            @Override
            public int hashCode() {
                return Objects.hash(mentor, start);
            }
        }

        private Consultations() {
            super(new TypeToken<ArrayList<Consultation>>() {
            }.getType());
        }

        @Override
        public String getTableName() {
            return "consultations.json";
        }

        @Override
        public Key getKey(Consultation elem) {
            return new Key(elem.mentor, elem.start);
        }
    }

    // таблица расписание
    public static class Schedule extends AbstractDbTable<Schedule.Key, Schedule.Value> {
        public static class Value {
            public final String mentor;
            public final int day_of_week;
            public final long start;
            public final long duration;

            public Value(@JsonProperty("mentor") String mentor,
                         @JsonProperty("day_of_week") int day_of_week,
                         @JsonProperty("start") long start,
                         @JsonProperty("duration") long duration) {
                this.mentor = mentor;
                this.day_of_week = day_of_week;
                this.start = start;
                this.duration = duration;
            }

            //getters
            public String getMentor() {
                return mentor;
            }

            public int getDay_of_week() {
                return day_of_week;
            }

            public long getStart() {
                return start;
            }

            public long getDuration() {
                return duration;
            }
        }

        public static class Key {
            public final String mentor;
            public final int day_of_week;
            public final long start;

            public Key(String mentor, int day_of_week, long start) {
                this.mentor = mentor;
                this.day_of_week = day_of_week;
                this.start = start;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Key key = (Key) o;
                return day_of_week == key.day_of_week &&
                        start == key.start &&
                        mentor.equals(key.mentor);
            }

            @Override
            public int hashCode() {
                return Objects.hash(mentor, day_of_week, start);
            }
        }

        private Schedule() {
            super(new TypeToken<ArrayList<Value>>() {
            }.getType());
        }

        @Override
        public String getTableName() {
            return "schedule.json";
        }

        @Override
        public Key getKey(Value elem) {
            return new Key(elem.mentor, elem.day_of_week, elem.start);
        }
    }

    // таблица настройки
    public static class Settings extends AbstractDbTable<String, Settings.Record> {
        public static class Record {
            public final String name;
            public final String value;

            public Record(@JsonProperty("name") String name,
                          @JsonProperty("value") String value) {
                this.name = name;
                this.value = value;
            }

            //getters
            public String getName() {
                return name;
            }

            public String getValue() {
                return value;
            }
        }

        private Settings() {
            super(new TypeToken<ArrayList<Record>>() {
            }.getType());
        }

        @Override
        public String getTableName() {
            return "settings.json";
        }

        @Override
        public String getKey(Record elem) {
            return elem.name;
        }
    }


    public static void main(String[] args) throws IOException {
//        INSTANCE.users.readAll();
//        System.out.println(INSTANCE.users.getAll());

        if (!DataBase.INSTANCE.users.put(new Users.User("login", "hash", "name", false, "c:/!/!.jpg")))
            System.out.println("Пользователь уже существует...");
        ;
        DataBase.INSTANCE.users.put(new Users.User("login2", "hash2", "name2", false, "c:/!/2!.jpg"));
        DataBase.INSTANCE.users.put(new Users.User("mazneff", "hash3", "Мазнев Валерий", true, "c:/!/m!.png"));

        DataBase.INSTANCE.settings.put(new Settings.Record("monthLimit", "6"));
        DataBase.INSTANCE.settings.put(new Settings.Record("dayLimit", "2"));
        DataBase.INSTANCE.settings.put(new Settings.Record("slot_duration", "1800000"));

        List<Users.User> list2 = INSTANCE.users.select(e -> e.is_mentor);
        System.out.println(list2);

        System.out.println(INSTANCE.users.findKey("login2"));

        System.out.println(INSTANCE.users.exists("123"));

        System.out.println(INSTANCE.users.exists("mazneff"));

        System.out.println(INSTANCE.users.getAll());
    }
}