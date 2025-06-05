from typing import List, Optional
from uuid import UUID
from sqlalchemy.orm import Session
from app.models.todo import Todo
from app.schemas.todo import TodoCreate, TodoUpdate

class TodoRepository:
    def __init__(self, db: Session):
        self.db = db
    
    def get_all(self) -> List[Todo]:
        return self.db.query(Todo).all()
    
    def get_by_id(self, todo_id: UUID) -> Optional[Todo]:
        return self.db.query(Todo).filter(Todo.id == todo_id).first()
    
    def create(self, todo_data: TodoCreate) -> Todo:
        todo = Todo(**todo_data.dict())
        self.db.add(todo)
        self.db.commit()
        self.db.refresh(todo)
        return todo
    
    def update(self, todo_id: UUID, todo_data: TodoUpdate) -> Optional[Todo]:
        todo = self.get_by_id(todo_id)
        if todo:
            update_data = todo_data.dict(exclude_unset=True)
            for field, value in update_data.items():
                setattr(todo, field, value)
            self.db.commit()
            self.db.refresh(todo)
        return todo
    
    def delete(self, todo_id: UUID) -> bool:
        todo = self.get_by_id(todo_id)
        if todo:
            self.db.delete(todo)
            self.db.commit()
            return True
        return False