import random
import json

# List of family names
family_names = ["Nguyen", "Tran", "Le", "Pham", "Hoang", "Phan", "Vu", "Dang", "Bui", "Do", "Ho", "Ngo", "Duong", "Ly"]

# Predefined teacher data
predefined_teachers = [
  {
    "role": "TEACHER",
    "email": "viet.trankhmtbk22@hcmut.edu.vn",
    "familyName": "Trần",
    "givenName": "Đại Việt",
    "teacherId": "2213951",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  },
  {
    "role": "TEACHER",
    "email": "khoa.nguyenmbk22351@hcmut.edu.vn",
    "familyName": "Nguyễn",
    "givenName": "Nhật Khoa",
    "teacherId": "2211629",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  },
  {
    "role": "TEACHER",
    "email": "anh.phamviet241103@hcmut.edu.vn",
    "familyName": "Phạm",
    "givenName": "Việt Anh",
    "teacherId": "2210128",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  },
  {
    "role": "TEACHER",
    "email": "nguyen.nguyengiabk22@hcmut.edu.vn",
    "familyName": "Nguyễn",
    "givenName": "Gia Nguyên",
    "teacherId": "2212303",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  },
  {
    "role": "TEACHER",
    "email": "khoa.lebku@hcmut.edu.vn",
    "familyName": "Lê",
    "givenName": "Đăng Khoa",
    "teacherId": "2211599",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  },
  {
    "role": "TEACHER",
    "email": "viet.phamhcmutk22@hcmut.edu.vn",
    "familyName": "Phạm",
    "givenName": "Văn Quốc Việt",
    "teacherId": "2211599",
    "phone": "0123456789",
    "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
  }
]

# Generate additional 35 teacher data
teachers = predefined_teachers[:]
for i in range(1, 36):
    family_name = random.choice(family_names)
    given_name = f"teacher{i}"
    teacher_id = f"221{random.randint(1000, 9999)}"
    email = f"{given_name}.bku@hcmut.edu.vn"
    teacher = {
        "role": "TEACHER",
        "email": email,
        "familyName": family_name,
        "givenName": given_name,
        "teacherId": teacher_id,
        "phone": "0123456789",
        "faculty": "Khoa Khoa học và Kỹ Thuật Máy tính"
    }
    teachers.append(teacher)

# Convert to JSON format
teachers_json = json.dumps(teachers, indent=2, ensure_ascii=False)

# Write to a file
with open("teacherData.json", "w", encoding="utf-8") as file:
    file.write(teachers_json)

print("Added 35 teachers to predefined list and saved to teachers.json")
