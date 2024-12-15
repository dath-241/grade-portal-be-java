import random
import json

# List of family names
family_names = ["Nguyen", "Tran", "Le", "Pham", "Hoang", "Phan", "Vu", "Dang", "Bui", "Do", "Ho", "Ngo", "Duong", "Ly"]

# Predefined data
predefined_students = [
  {
    "role": "STUDENT",
    "email": "trandaiviet78@gmail.com",
    "familyName": "Trần",
    "givenName": "Đại Việt",
    "studentId": "2213951",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  },
  {
    "role": "STUDENT",
    "email": "khoa.nguyenmbk22351@hcmut.edu.vn",
    "familyName": "Nguyễn",
    "givenName": "Nhật Khoa",
    "studentId": "2211629",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  },
  {
    "role": "STUDENT",
    "email": "anh.phamviet241103@hcmut.edu.vn",
    "familyName": "Phạm",
    "givenName": "Việt Anh",
    "studentId": "2210128",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  },
  {
    "role": "STUDENT",
    "email": "nguyen.nguyengiabk22@hcmut.edu.vn",
    "familyName": "Nguyễn",
    "givenName": "Gia Nguyên",
    "studentId": "2212303",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  },
  {
    "role": "STUDENT",
    "email": "khoa.lebku@hcmut.edu.vn",
    "familyName": "Lê",
    "givenName": "Đăng Khoa",
    "studentId": "2211599",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  },
  {
    "role": "STUDENT",
    "email": "viet.phamhcmutk22@hcmut.edu.vn",
    "familyName": "Phạm",
    "givenName": "Văn Quốc Việt",
    "studentId": "2213950",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  }
]

# Generate additional 100 student data
students = predefined_students[:]
for i in range(1, 100):
    family_name = random.choice(family_names)
    given_name = f"student{i}"
    student_id = f"221{random.randint(1000, 9999)}"
    email = f"{given_name}.bku@hcmut.edu.vn"
    student = {
        "role": "STUDENT",
        "email": email,
        "familyName": family_name,
        "givenName": given_name,
        "studentId": student_id,
        "phone": "0123456789",
        "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
    }
    students.append(student)

# Convert to JSON format
students_json = json.dumps(students, indent=2, ensure_ascii=False)

# Write to a file
with open("studentData.json", "w", encoding="utf-8") as file:
    file.write(students_json)

print("Added 35 students to predefined list and saved to students.json")
