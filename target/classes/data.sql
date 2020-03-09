INSERT INTO mars.institutional_user (id, email, first_name, last_name, phone, user_name,password, role)
    VALUES (1, 'teszt.elek@teszt.hu', 'Teszt', 'Elek', '1234 567', 'tesztelek','$2y$10$HssVEkgvwfRehsw7Dl9nfek5PNwdWfiSkF7A2TaNiAeCRFH8691/m','ROLE_INST');
INSERT INTO mars.institution (id, name, email, zip_code, city, address, description, creator_id)
    VALUES (1, 'Teszt Alapítvány', 'info@teszt.hu', 1345, 'Budapest',
            'Béla utca 3.', 'Az alapítvány segíti a tesztelésben :)', 1);
