package bbamrin.animehelperreborn.utils;

import android.util.Log;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.internalModel.Classification;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.AnimeModel;


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
        for(Genre g: genres){
            query +=g.getShikimoriNumber();
            query +=",";
        }
        StringBuilder builder = new StringBuilder(query);
        builder.setCharAt(query.length()-1,' ');
        return builder.toString();
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
            list.add(ShoujoAi);
            Genre Josei = new Genre();
            Josei.setTextGenre("Дзёсей");
            list.add(Josei);
            Genre Seinen = new Genre();
            Seinen.setTextGenre("Сейнен");
            list.add(Seinen);
            Genre ShounenAi = new Genre();
            ShounenAi.setTextGenre("Сёнен Ай");
            list.add(ShounenAi);
            Genre Shounen = new Genre();
            Shounen.setTextGenre("Сёнен");
            list.add(Shounen);
            Genre Shoujo = new Genre();
            Shoujo.setTextGenre("Сёдзе");
            list.add(Shoujo);
            Genre Kids = new Genre();
            Kids.setTextGenre("Детское");
            list.add(Kids);

        } else if (classification == StaticVars.SETTING) {
            Genre Space = new Genre();
            Space.setTextGenre("Космос");
            //just a stub for testing, in future i will add shikimoriNumber to all genres
            Space.setShikimoriNumber("29");
            list.add(Space);
            Genre Historical = new Genre();
            Historical.setTextGenre("Исторический");
            list.add(Historical);
            Genre Fantasy = new Genre();
            Fantasy.setTextGenre("Фэнтези");
            list.add(Fantasy);
            Genre School = new Genre();
            School.setTextGenre("Школа");
            list.add(School);
            Genre SliceOfLife = new Genre();
            SliceOfLife.setTextGenre("Повседневность");
            list.add(SliceOfLife);

        } else if (classification == StaticVars.ANTOURAGE) {
            Genre Magic = new Genre();
            Magic.setTextGenre("Магия");
            list.add(Magic);
            Genre Adventure = new Genre();
            Adventure.setTextGenre("Приключения");
            list.add(Adventure);
            Genre Police = new Genre();
            Police.setTextGenre("Полиция");
            list.add(Police);
            Genre Samurai = new Genre();
            Samurai.setTextGenre("Самураи");
            list.add(Samurai);
            Genre Music = new Genre();
            Music.setTextGenre("Музыка");
            list.add(Music);
            Genre Vampire = new Genre();
            Vampire.setTextGenre("Вампиры");
            list.add(Vampire);
            Genre Sports = new Genre();
            Sports.setTextGenre("Спорт");
            list.add(Sports);
            Genre Supernatural = new Genre();
            Supernatural.setTextGenre("Сверхъестественное");
            list.add(Supernatural);
            Genre Thriller = new Genre();
            Thriller.setTextGenre("Триллер");
            list.add(Thriller);
            Genre SciFi = new Genre();
            SciFi.setTextGenre("Фантастика");
            list.add(SciFi);
            Genre SuperPower = new Genre();
            SuperPower.setTextGenre("Супер сила");
            list.add(SuperPower);
            Genre Military = new Genre();
            Military.setTextGenre("Военное");
            list.add(Military);
            Genre Mystery = new Genre();
            Mystery.setTextGenre("Детектив");
            list.add(Mystery);
            Genre Cars = new Genre();
            Cars.setTextGenre("Машины");
            list.add(Cars);
            Genre MartialArts = new Genre();
            MartialArts.setTextGenre("Боевые искусства");
            list.add(MartialArts);
            Genre Game = new Genre();
            Game.setTextGenre("Игры");
            list.add(Game);
            Genre Dementia = new Genre();
            Dementia.setTextGenre("Безумие");
            list.add(Dementia);

        } else if (classification == StaticVars.STYLE_OF_NARRATIVE) {
            Genre Drama = new Genre();
            Drama.setTextGenre("Драма");
            list.add(Drama);
            Genre Psychological = new Genre();
            Psychological.setTextGenre("Психологическое");
            list.add(Psychological);
            Genre Action = new Genre();
            Action.setTextGenre("Экшен");
            list.add(Action);
            Genre Comedy = new Genre();
            Comedy.setTextGenre("Комедия");
            list.add(Comedy);
            Genre Ecchi = new Genre();
            Ecchi.setTextGenre("Этти");
            list.add(Ecchi);
            Genre Horror = new Genre();
            Horror.setTextGenre("Ужасы");
            list.add(Horror);
            Genre Parody = new Genre();
            Parody.setTextGenre("Пародия");
            list.add(Parody);
            Genre Romance = new Genre();
            Romance.setTextGenre("Романтика");
            list.add(Romance);
            Genre Harem = new Genre();
            Harem.setTextGenre("Гарем");
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
