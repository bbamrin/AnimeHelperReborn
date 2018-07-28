package bbamrin.animehelperreborn.utils;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.internalModel.Classification;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;


public class Utils {
    public static String generateChosenGenresString(ArrayList<Genre> genres) {
        String genresToReturn = "";
        if (genres != null) {
            for (Genre g : genres) {
                if (g.isChosen()) {
                    genresToReturn += g.getTextGenre() + ", ";
                }
            }
            if (genresToReturn != "") {
                StringBuilder builder = new StringBuilder(genresToReturn);
                builder.setCharAt(genresToReturn.length() - 2, '.');
                genresToReturn = builder.toString();
            }

        }
        return genresToReturn;

    }

    public static String generateGenresQueryForShikimori(ArrayList<Genre> genres){
        String query = "";
        if (genres.size()!=0){
            for(Genre g: genres){
                query +=g.getShikimoriNumber();
                query +=",";
            }
            StringBuilder builder = new StringBuilder(query);
            builder.setCharAt(query.length()-1,' ');
            return builder.toString();
        } else return query;

    }

    public static ArrayList<AnimeModel> addOnlyNewData(ArrayList<AnimeModel> arrayListOld, ArrayList<AnimeModel> arrayListNew){
        ArrayList<AnimeModel> list = new ArrayList<>();
        list.addAll(arrayListOld);
        for (int i =0; i < arrayListNew.size();++i){
            int counter = 0;
            for(int j = 0;j < arrayListOld.size();++j){
                if (arrayListNew.get(i).equals(arrayListOld.get(j))){
                    counter++;
                }
            }
            if (counter ==0){
                list.add(arrayListNew.get(i));
            }
        }

        return list;
    }

    public static ArrayList<Genre> getGenresList(String classification) {
        ArrayList<Genre> list = new ArrayList<>();
        if (classification == StaticVars.TARGET_AUDIENCE) {
            Genre ShoujoAi = new Genre();
            ShoujoAi.setTextGenre("Сёдзе Ай");
            ShoujoAi.setShikimoriNumber("26");
            list.add(ShoujoAi);
            Genre Josei = new Genre();
            Josei.setTextGenre("Дзёсей");
            Josei.setShikimoriNumber("43");
            list.add(Josei);
            Genre Seinen = new Genre();
            Seinen.setTextGenre("Сейнен");
            Seinen.setShikimoriNumber("42");
            list.add(Seinen);
            Genre ShounenAi = new Genre();
            ShounenAi.setTextGenre("Сёнен Ай");
            ShounenAi.setShikimoriNumber("28");
            list.add(ShounenAi);
            Genre Shounen = new Genre();
            Shounen.setTextGenre("Сёнен");
            Shounen.setShikimoriNumber("27");
            list.add(Shounen);
            Genre Shoujo = new Genre();
            Shoujo.setTextGenre("Сёдзе");
            Shoujo.setShikimoriNumber("25");
            list.add(Shoujo);
            Genre Kids = new Genre();
            Kids.setTextGenre("Детское");
            Kids.setShikimoriNumber("15");
            list.add(Kids);

        } else if (classification == StaticVars.SETTING) {
            Genre Space = new Genre();
            Space.setTextGenre("Космос");
            Space.setShikimoriNumber("29");
            list.add(Space);
            Genre Historical = new Genre();
            Historical.setTextGenre("Исторический");
            Historical.setShikimoriNumber("13");
            list.add(Historical);
            Genre Fantasy = new Genre();
            Fantasy.setTextGenre("Фэнтези");
            Fantasy.setShikimoriNumber("10");
            list.add(Fantasy);
            Genre School = new Genre();
            School.setTextGenre("Школа");
            School.setShikimoriNumber("23");
            list.add(School);
            Genre SliceofLife = new Genre();
            SliceofLife.setTextGenre("Повседневность");
            SliceofLife.setShikimoriNumber("36");
            list.add(SliceofLife);

        } else if (classification == StaticVars.ANTOURAGE) {
            Genre Magic = new Genre();
            Magic.setTextGenre("Магия");
            Magic.setShikimoriNumber("16");
            list.add(Magic);
            Genre Adventure = new Genre();
            Adventure.setTextGenre("Приключения");
            Adventure.setShikimoriNumber("2");
            list.add(Adventure);
            Genre Police = new Genre();
            Police.setTextGenre("Полиция");
            Police.setShikimoriNumber("39");
            list.add(Police);
            Genre Samurai = new Genre();
            Samurai.setTextGenre("Самураи");
            Samurai.setShikimoriNumber("21");
            list.add(Samurai);
            Genre Music = new Genre();
            Music.setTextGenre("Музыка");
            Music.setShikimoriNumber("19");
            list.add(Music);
            Genre Vampire = new Genre();
            Vampire.setTextGenre("Вампиры");
            Vampire.setShikimoriNumber("32");
            list.add(Vampire);
            Genre Sports = new Genre();
            Sports.setTextGenre("Спорт");
            Sports.setShikimoriNumber("30");
            list.add(Sports);
            Genre Supernatural = new Genre();
            Supernatural.setTextGenre("Сверхъестественное");
            Supernatural.setShikimoriNumber("37");
            list.add(Supernatural);
            Genre Thriller = new Genre();
            Thriller.setTextGenre("Триллер");
            Thriller.setShikimoriNumber("41");
            list.add(Thriller);
            Genre SciFi = new Genre();
            SciFi.setTextGenre("Фантастика");
            SciFi.setShikimoriNumber("24");
            list.add(SciFi);
            Genre SuperPower = new Genre();
            SuperPower.setTextGenre("Супер сила");
            SuperPower.setShikimoriNumber("31");
            list.add(SuperPower);
            Genre Military = new Genre();
            Military.setTextGenre("Военное");
            Military.setShikimoriNumber("38");
            list.add(Military);
            Genre Mystery = new Genre();
            Mystery.setTextGenre("Детектив");
            Mystery.setShikimoriNumber("7");
            list.add(Mystery);
            Genre Cars = new Genre();
            Cars.setTextGenre("Машины");
            Cars.setShikimoriNumber("3");
            list.add(Cars);
            Genre MartialArts = new Genre();
            MartialArts.setTextGenre("Боевые искусства");
            MartialArts.setShikimoriNumber("17");
            list.add(MartialArts);
            Genre Game = new Genre();
            Game.setTextGenre("Игры");
            Game.setShikimoriNumber("11");
            list.add(Game);
            Genre Dementia = new Genre();
            Dementia.setTextGenre("Безумие");
            Dementia.setShikimoriNumber("5");
            list.add(Dementia);

        } else if (classification == StaticVars.STYLE_OF_NARRATIVE) {
            Genre Drama = new Genre();
            Drama.setTextGenre("Драма");
            Drama.setShikimoriNumber("8");
            list.add(Drama);
            Genre Psychological = new Genre();
            Psychological.setTextGenre("Психологическое");
            Psychological.setShikimoriNumber("40");
            list.add(Psychological);
            Genre Action = new Genre();
            Action.setTextGenre("Экшен");
            Action.setShikimoriNumber("1");
            list.add(Action);
            Genre Comedy = new Genre();
            Comedy.setTextGenre("Комедия");
            Comedy.setShikimoriNumber("4");
            list.add(Comedy);
            Genre Ecchi = new Genre();
            Ecchi.setTextGenre("Этти");
            Ecchi.setShikimoriNumber("9");
            list.add(Ecchi);
            Genre Horror = new Genre();
            Horror.setTextGenre("Ужасы");
            Horror.setShikimoriNumber("14");
            list.add(Horror);
            Genre Parody = new Genre();
            Parody.setTextGenre("Пародия");
            Parody.setShikimoriNumber("20");
            list.add(Parody);
            Genre Romance = new Genre();
            Romance.setTextGenre("Романтика");
            Romance.setShikimoriNumber("22");
            list.add(Romance);
            Genre Harem = new Genre();
            Harem.setTextGenre("Гарем");
            Harem.setShikimoriNumber("35");
            list.add(Harem);

        } else {
            Genre genre = new Genre();
            genre.setTextGenre("nothing");
            list.add(genre);
        }

        return list;
    }

    public static ArrayList<Classification> getClassificationList(){
        ArrayList<Classification> classifications = new ArrayList<>();
        classifications.add(new Classification(StaticVars.ANTOURAGE,getGenresList(StaticVars.ANTOURAGE)));
        classifications.add(new Classification(StaticVars.STYLE_OF_NARRATIVE,getGenresList(StaticVars.STYLE_OF_NARRATIVE)));
        classifications.add(new Classification(StaticVars.SETTING,getGenresList(StaticVars.SETTING)));
        classifications.add(new Classification(StaticVars.TARGET_AUDIENCE,getGenresList(StaticVars.TARGET_AUDIENCE)));
        return classifications;
    }


}
