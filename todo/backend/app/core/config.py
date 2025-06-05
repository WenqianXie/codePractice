import os
from dotenv import load_dotenv

# Load environment variables
load_dotenv()

class Settings:
    DATABASE_URL: str = os.getenv("DATABASE_URL", "postgresql://todo_user:todo_password@localhost:5432/todo_db")
    
settings = Settings()