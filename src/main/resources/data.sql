INSERT INTO mars.institutional_user (id, email, first_name, last_name, phone, user_name,password, role)
    VALUES (1, 'teszt.elek@teszt.hu', 'Teszt', 'Elek', '1234 567', 'tesztelek','$2y$10$HssVEkgvwfRehsw7Dl9nfek5PNwdWfiSkF7A2TaNiAeCRFH8691/m','ROLE_INST');
INSERT INTO institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (1, 'Kutya', 'info1@teszt.hu', 1345, 'Budapest',
            'Béla utca 3.', 'Az alapítvány segíti a tesztelésben :)', 1, 'DIAGNOSTIC_CENTER');
INSERT INTO institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (2, 'Macska', 'info2@teszt.hu', 1345, 'Budapest',
            'Béla utca 3.', 'Az alapítvány segíti a tesztelésben :)', 1, 'THERAPY');
INSERT INTO institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (3, 'Ló', 'info3@teszt.hu', 1345, 'Budapest',
            'Béla utca 3.', 'Az alapítvány segíti a tesztelésben :)', 1, 'THERAPY');
INSERT INTO institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (4, 'Alpaca', 'info4@teszt.hu', 1345, 'Budapest',
            'Béla utca 3.', 'Az alapítvány segíti a tesztelésben :)', 1, 'ETC');
INSERT INTO institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (5, 'Kapibara', 'info5@teszt.hu', 1345, 'Budapest',
            'Béla utca 3.', 'Az alapítvány segíti a tesztelésben :)', 1, 'ETC');
INSERT INTO institution (id, name, email, zip_code, city, address, description, creator_id, institution_type)
    VALUES (6, 'Apache helikopter', 'info6@teszt.hu', 1345, 'Budapest',
            'Béla utca 3.', 'Az alapítvány segíti a tesztelésben :)', 1, 'DAY_CARE');