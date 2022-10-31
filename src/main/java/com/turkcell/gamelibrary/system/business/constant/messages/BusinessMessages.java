package com.turkcell.gamelibrary.system.business.constant.messages;

public final class BusinessMessages {

    public final class GeneralSuccessMessages{
        public static final String SUCCESS_LIST = "Listed Successfully.";
        public static final String SUCCESS_GET = "Getted Successfully.";
        public static final String SUCCESS_ADD = "Added Successfully.";
        public static final String SUCCESS_UPDATE = "Updated Successfully.";
        public static final String SUCCESS_DELETE = "Deleted Successfully.";
        public static final String SUCCESS_LIST_PAGED = "Paged Listed Successfully.";
        public static final String SUCCESS_LIST_SORTED = "Sorted Listed Successfully.";
        public static final String SUCCESS_LOG_IN = "Logged in successfully.";
        public static final String SUCCESS_REGISTER = "User registered successfully.";
    }

    public final class GameExceptionMessages{
        public static final String GAME_NOT_FOUND = "Oyun bulunamadı.";
        public static final String GAME_NAME_ALREADY_EXISTS = "Aynı isme sahip bir oyun zaten kütüphanede mevcut.";
        public static final String GAME_CREATION_DATE_ERROR = "Oyun ekleme tarihini bugünden sonra olarak güncelleyemezsiniz.";
    }

    public final class CampaignExceptionMessages{
        public static final String CAMPAIGN_NOT_FOUND = "Kampanya bulunamadı.";
        public static final String CAMPAIGN_TITLE_ALREADY_EXISTS = "Aynı isme sahip bir kampanya zaten kütüphanede mevcut.";
        public static final String CAMPAIGN_CREATION_DATE_ERROR = "Oluşturulmak istenen kampanyanın başlangıç tarihi bugünden önce olamaz";
        public static final String CAMPAIGN_CREATION_DATES_ERROR = "Kampanya başlangıç tarihi bitiş tarihinden sonra olamaz";
    }

    public final class CommentExceptionMessages{
        public static final String COMMENT_NOT_FOUND = "Yorum bulunamadı.";
        public static final String COMMENT_CANNOT_DISPLAY = "Yorum şuanda görüntülenemiyor.";
        public static final String COMMENT_LIST_EMPTY_MESSAGE = "Burada hiç yorum yok, ilk yorum yapan sen ol.";
    }

    public class UserExceptionMessages {
        public static final String USER_NOT_FOUND = "User not found!";
        public static final String EMAIL_ALREADY_EXISTS = "Email has already exists.";
        public static final String USERNAME_EXISTS = "Nickname has already exists.";
    }
}
