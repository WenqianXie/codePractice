from fastapi import APIRouter, Depends, status
from sqlalchemy.orm import Session
from typing import List
from uuid import UUID
from app.core.database import get_db
from app.services.todo_service import TodoService
from app.schemas.todo import TodoCreate, TodoUpdate, TodoResponse

router = APIRouter(
    prefix="/todos",
    tags=["todos"],
    responses={404: {"description": "Not found"}}
)

def get_todo_service(db: Session = Depends(get_db)) -> TodoService:
    return TodoService(db)

@router.get("", response_model=List[TodoResponse])
async def get_todos(service: TodoService = Depends(get_todo_service)):
    return service.get_all_todos()

@router.get("/{todo_id}", response_model=TodoResponse)
async def get_todo(todo_id: UUID, service: TodoService = Depends(get_todo_service)):
    return service.get_todo_by_id(todo_id)

@router.post("", response_model=TodoResponse, status_code=status.HTTP_201_CREATED)
async def create_todo(todo: TodoCreate, service: TodoService = Depends(get_todo_service)):
    return service.create_todo(todo)

@router.put("/{todo_id}", response_model=TodoResponse)
async def update_todo(todo_id: UUID, todo: TodoUpdate, service: TodoService = Depends(get_todo_service)):
    return service.update_todo(todo_id, todo)

@router.delete("/{todo_id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_todo(todo_id: UUID, service: TodoService = Depends(get_todo_service)):
    service.delete_todo(todo_id)
    return None