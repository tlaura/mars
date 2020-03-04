INSERT INTO institutional_user (id, email, first_name, last_name, phone, user_name)
    VALUES (1, 'teszt.elek@teszt.hu', 'Teszt', 'Elek', '1234 567', 'tesztelek');
INSERT INTO institution (id, name, email, zip_code, city, address, description, creator_id)
    VALUES (1, 'Teszt Alapítvány', 'info@teszt.hu', 1345, 'Budapest',
            'Béla utca 3.', 'Az alapítvány segíti a tesztelésben :)', 1);