INSERT INTO categories (name, slug, active, description, display_order, created_at, updated_at) VALUES
('Electronics', 'electronics', true, 'Gadgets, devices and technology', 1, NOW(), NOW()),
('Computers & Accessories', 'computers-accessories', true, 'Laptops, desktops and peripherals', 2, NOW(), NOW()),
('Fashion', 'fashion', true, 'Clothing, shoes and jewelry', 3, NOW(), NOW()),
('Home & Kitchen', 'home-kitchen', true, 'Furniture, decor and appliances', 4, NOW(), NOW()),
('Sports & Outdoors', 'sports-outdoors', true, 'Exercise equipment and outdoor gear', 5, NOW(), NOW()),
('Books', 'books', true, 'Fiction, non-fiction and educational', 6, NOW(), NOW()),
('Toys & Games', 'toys-games', true, 'Fun for all ages', 7, NOW(), NOW()),
('Beauty & Personal Care', 'beauty-personal-care', true, 'Makeup, skincare and hair care', 8, NOW(), NOW()),
('Automotive', 'automotive', true, 'Car parts and accessories', 9, NOW(), NOW()),
('Health & Household', 'health-household', true, 'Vitamins, supplements and cleaning', 10, NOW(), NOW());


INSERT INTO categories (name, slug, parent_id, active, description, display_order, created_at, updated_at) VALUES
('Cell Phones', 'cell-phones', (SELECT id FROM categories WHERE slug = 'electronics'), true, 'Smartphones and mobile phones', 1, NOW(), NOW()),
('Headphones', 'headphones', (SELECT id FROM categories WHERE slug = 'electronics'), true, 'Over-ear, in-ear and wireless', 2, NOW(), NOW()),
('Cameras', 'cameras', (SELECT id FROM categories WHERE slug = 'electronics'), true, 'DSLR, mirrorless and point-and-shoot', 3, NOW(), NOW()),
('Television & Video', 'television-video', (SELECT id FROM categories WHERE slug = 'electronics'), true, 'TVs, projectors and streaming devices', 4, NOW(), NOW()),
('Wearable Technology', 'wearable-technology', (SELECT id FROM categories WHERE slug = 'electronics'), true, 'Smartwatches and fitness trackers', 5, NOW(), NOW());

INSERT INTO categories (name, slug, parent_id, active, description, display_order, created_at, updated_at) VALUES
('Laptops', 'laptops', (SELECT id FROM categories WHERE slug = 'computers-accessories'), true, 'Notebooks and ultrabooks', 1, NOW(), NOW()),
('Desktops', 'desktops', (SELECT id FROM categories WHERE slug = 'computers-accessories'), true, 'Tower PCs and all-in-ones', 2, NOW(), NOW()),
('Monitors', 'monitors', (SELECT id FROM categories WHERE slug = 'computers-accessories'), true, 'Computer screens and displays', 3, NOW(), NOW()),
('Storage', 'storage', (SELECT id FROM categories WHERE slug = 'computers-accessories'), true, 'Hard drives, SSDs and USB drives', 4, NOW(), NOW()),
('Networking', 'networking', (SELECT id FROM categories WHERE slug = 'computers-accessories'), true, 'Routers, modems and switches', 5, NOW(), NOW());

INSERT INTO categories (name, slug, parent_id, active, description, display_order, created_at, updated_at) VALUES
('Men''s Clothing', 'mens-clothing', (SELECT id FROM categories WHERE slug = 'fashion'), true, 'Shirts, pants and jackets for men', 1, NOW(), NOW()),
('Women''s Clothing', 'womens-clothing', (SELECT id FROM categories WHERE slug = 'fashion'), true, 'Dresses, tops and skirts for women', 2, NOW(), NOW()),
('Shoes', 'shoes', (SELECT id FROM categories WHERE slug = 'fashion'), true, 'Sneakers, boots and sandals', 3, NOW(), NOW()),
('Watches', 'watches', (SELECT id FROM categories WHERE slug = 'fashion'), true, 'Analog and digital watches', 4, NOW(), NOW()),
('Jewelry', 'jewelry', (SELECT id FROM categories WHERE slug = 'fashion'), true, 'Necklaces, rings and earrings', 5, NOW(), NOW());

INSERT INTO categories (name, slug, parent_id, active, description, display_order, created_at, updated_at) VALUES
('Furniture', 'furniture', (SELECT id FROM categories WHERE slug = 'home-kitchen'), true, 'Sofas, tables and chairs', 1, NOW(), NOW()),
('Kitchen & Dining', 'kitchen-dining', (SELECT id FROM categories WHERE slug = 'home-kitchen'), true, 'Cookware, cutlery and appliances', 2, NOW(), NOW()),
('Bedding', 'bedding', (SELECT id FROM categories WHERE slug = 'home-kitchen'), true, 'Sheets, comforters and pillows', 3, NOW(), NOW()),
('Decor', 'decor', (SELECT id FROM categories WHERE slug = 'home-kitchen'), true, 'Wall art, rugs and lighting', 4, NOW(), NOW()),
('Storage & Organization', 'storage-organization', (SELECT id FROM categories WHERE slug = 'home-kitchen'), true, 'Bins, racks and shelves', 5, NOW(), NOW());

INSERT INTO categories (name, slug, parent_id, active, description, display_order, created_at, updated_at) VALUES
('Exercise & Fitness', 'exercise-fitness', (SELECT id FROM categories WHERE slug = 'sports-outdoors'), true, 'Cardio, strength and yoga', 1, NOW(), NOW()),
('Camping & Hiking', 'camping-hiking', (SELECT id FROM categories WHERE slug = 'sports-outdoors'), true, 'Tents, backpacks and gear', 2, NOW(), NOW()),
('Cycling', 'cycling', (SELECT id FROM categories WHERE slug = 'sports-outdoors'), true, 'Bikes, helmets and accessories', 3, NOW(), NOW()),
('Team Sports', 'team-sports', (SELECT id FROM categories WHERE slug = 'sports-outdoors'), true, 'Soccer, basketball and football', 4, NOW(), NOW()),
('Fishing', 'fishing', (SELECT id FROM categories WHERE slug = 'sports-outdoors'), true, 'Rods, reels and bait', 5, NOW(), NOW());