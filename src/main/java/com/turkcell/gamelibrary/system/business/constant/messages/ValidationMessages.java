package com.turkcell.gamelibrary.system.business.constant.messages;

public final class ValidationMessages {

    public final class GeneralValidationMessages{
        public static final String ID_CANNOT_LESS_THEN_ONE = "ID değeri negatif bir değer olamaz!";
    }
    public final class UserValidationMessages{
        public static final String NAME_VALIDATION = "İsim alanı 2-20 karakter arasında, özel karakter ve sayı içermeyecek şekilde olabilir.";
        public static final String LAST_NAME_VALIDATION = "Soy isim alanı 2-20 karakter arasında, özel karakter ve sayı içermeyecek şekilde olabilir.";
        public static final String NICK_NAME_VALIDATION = "Takma ad alanı 2-20 karakter arasında, '-_*' özel karakterleri ve sayı içerebilecek şekilde olabilir.";
        public static final String EMAIL_VALIDATION = "Uygun formatta bir mail girmelisiniz. Örnek: 'alihaydar_123@gmail.com'";
        public static final String PASSWORD_VALIDATION_ERROR = "Password must contain at least digit, lowercase and uppercase and at least once special chars and password size has to be 8-20 chars.";
    }

    public final class GameValidationMessages{
        public static final String GAME_NAME_VALIDATION_ERROR = "Oyun adı 3-50 karakter aralığında nümerik ve alfabetik karakterlerden oluşabilir.";
        public static final String GAME_EXPLANATION_VALIDATION_ERROR = "Oyun açıklaması 5-200 karakter aralığında nümerik, alfabetik ve whitespace karakterlerden oluşabilir.";
        public static final String GAME_MANUFACTURER_VALIDATION_ERROR = "Oyun üreticisi adı 2-50 karakter aralığında nümerik ve alfabetik karakterlerden oluşmalıdır.";
        public static final String GAME_PRICE_VALIDATION_ERROR = "Oyun fiyatı negatif olamaz!";
    }

    public final class CampaignValidationMessages{
        public static final String CAMPAIGN_TITLE_VALIDATION_ERROR = "Kampanya başlığı 3-50 karakter aralığında olmalı.";
        public static final String CAMPAIGN_DESCRIPTION_VALIDATION_ERROR = "Kampanya açıklaması 5-200 karakter aralığında nümerik, alfabetik ve whitespace karakterlerden oluşabilir.";
        public static final String CAMPAIGN_DISCOUNT_AMOUNT_VALIDATION_ERROR = "Kampanyaya ait indirim tutarı negatif olamaz.";
    }

    public final class CommentValidationMessages{
        public static final String COMMENT_BODY_VALIDATION="Yorum en fazla 1000 karakterden oluşabilir.";
        public static final String COMMENT_NOT_SELECTED_GAME="Yorum yapacağınız oyunu seçmediniz.";
    }
}
