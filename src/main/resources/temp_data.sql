INSERT INTO mars.provider_account (id, name, provider_service_name, password, email, phone, role, zipcode, city,
                                   address, age_group_min, age_group_max)
VALUES (1, 'test', 'tesztelek', '$2y$10$HssVEkgvwfRehsw7Dl9nfek5PNwdWfiSkF7A2TaNiAeCRFH8691/m', 'pecske92@gmail.com',
        '1234 567', 'ROLE_PROVIDER', '9500', 'pest', 'asd', 0, 79);

INSERT INTO mars.institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (1, 'Kutya', 'info1@teszt.hu', 1056, 'Budapest',
            'Irányi utca 3.', 'Az alapítvány segíti a tesztelésben :)', 1, 'DIAGNOSTIC_CENTER');

INSERT INTO mars.institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (2, 'Macska', 'info2@teszt.hu', 1089, 'Budapest',
            'Orczy út 43.', 'Az alapítvány segíti a tesztelésben :)', 1, 'THERAPY');
INSERT INTO mars.institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (3, 'Ló', 'info3@teszt.hu', 1066, 'Budapest',
            'Teréz krt. 38.', 'Az alapítvány segíti a tesztelésben :)', 1, 'THERAPY');
INSERT INTO mars.institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (4, 'Alpaca', 'info4@teszt.hu', 1074, 'Budapest',
            'Dohány utca 12.', 'Az alapítvány segíti a tesztelésben :)', 1, 'ETC');

INSERT INTO mars.institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (5, 'Kapibara', 'info5@teszt.hu', 1052, 'Budapest',
            'Semmelweis u. 2.', 'Az alapítvány segíti a tesztelésben :)', 1, 'ETC');
INSERT INTO mars.institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (6, 'Apache helikopter', 'info6@teszt.hu', 1051, 'Budapest',
            'Sas utca 25.', 'Az alapítvány segíti a tesztelésben :)', 1, 'DAY_CARE');