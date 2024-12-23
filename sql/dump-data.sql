INSERT INTO public.homestay (name, description, type, host_id, status, phone_number, address, longitude, latitude, geom, images, guests, bedrooms, bathrooms, extra_data, version, created_at, created_by, updated_at, updated_by) VALUES ('Ronin Engineer Hotel', 'Ronin Engineer Hotel', 1, 1, 1, '0362228388', null, 105.85054073130831, 21.010219168557075, '0101000020110F00008BBD6B89877966417F88029404424241', null, 2, null, null, null, 1, '2024-12-20 15:59:14.977000 +00:00', null, null, null);

INSERT INTO public.homestay_availability (homestay_id, date, price, status) VALUES (1, '2024-12-20', 20, 0);
INSERT INTO public.homestay_availability (homestay_id, date, price, status) VALUES (1, '2024-12-21', 20, 0);
INSERT INTO public.homestay_availability (homestay_id, date, price, status) VALUES (1, '2024-12-23', 20, 0);
INSERT INTO public.homestay_availability (homestay_id, date, price, status) VALUES (1, '2024-12-22', 20, 2);

