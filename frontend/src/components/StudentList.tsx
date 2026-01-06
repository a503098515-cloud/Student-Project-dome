import React from 'react';
import type { Student } from '../types';

// Define props
interface Props {
  students: Student[];
  handleEdit: (student: Student) => void;
  handleDelete: (id: number) => void;
}

const StudentList: React.FC<Props> = ({ students, handleEdit, handleDelete }) => {
  return (
    <div className="list-section">
      <h3>Student List</h3>
      <table>
        <thead>
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>GPA</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {students.map(student => (
            <tr key={student.id}>
              <td>{student.firstName}</td>
              <td>{student.lastName}</td>
              <td>{student.email}</td>
              <td>{student.gpa}</td>
              <td>
                <button className="btn-edit" onClick={() => handleEdit(student)}>Edit</button>
                {/* Check if ID exists before deleting */}
                <button className="btn-delete" onClick={() => student.id && handleDelete(student.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default StudentList;