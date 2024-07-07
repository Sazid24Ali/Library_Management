-- use lib_management;

INSERT INTO book_details_entity (book_code, book_name, Author, subject_category, edition) VALUES
(1001, 'Introduction to Algorithms', 'Thomas H. Cormen', 'Computer Science', '3rd'),
(1002, 'Clean Code', 'Robert C. Martin', 'Software Engineering', '1st'),
(1003, 'Artificial Intelligence: A Modern Approach', 'Stuart Russell', 'Artificial Intelligence', '4th'),
(1004, 'Database System Concepts', 'Abraham Silberschatz', 'Database', '6th'),
(1005, 'Operating System Concepts', 'Abraham Silberschatz', 'Operating Systems', '9th');


-- INSERT INTO student_entity (student_roll_No, student_Name, phone_Number, branch, year_Of_Passing) VALUES
-- (2001, 'John Doe', 1234567890, 'Computer Science', 2023),
-- (2002, 'Jane Smith', 1234567891, 'Information Technology', 2024),
-- (2003, 'Robert Brown', 1234567892, 'Computer Science', 2023),
-- (2004, 'Emily Davis', 1234567893, 'Software Engineering', 2025),
-- (2005, 'Michael Wilson', 1234567894, 'Artificial Intelligence', 2024);

INSERT INTO books_entity (book_id, Book_Code, status, student_roll_No, Date_Of_Allotment) VALUES
(1, 1001, 'Available', NULL, NULL),
(2, 1002, 'Available', NULL, NULL),
(3, 1003, 'Available', NULL, NULL),
(4, 1004, 'Available',NULL, NULL),
(5, 1005, 'Available', NULL, NULL),
(6, 1001, 'Available', NULL, NULL),
(7, 1002, 'Available', NULL, NULL),
(8, 1003, 'Available', NULL, NULL),
(9, 1004, 'Available', NULL, NULL),
(10, 1005, 'Available',NULL, NULL);

-- select * from books_entity where status="Available";
-- select * from books_entity where status="Available";
-- select b.*,s.* from books_entity b , student_entity s where s.student_roll_no=b.student_roll_no;
