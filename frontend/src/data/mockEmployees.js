import { ROLES } from './roles';

export const mockEmployees = [
  {
    id: 1,
    name: "Sarah Johnson",
    role: ROLES.EMPLOYEE,
    email: "sarah.j@company.com",
    department: "Engineering",
    joinDate: "2022-03-15",
    salary: 85000
  },
  {
    id: 2,
    name: "Michael Chen",
    role: ROLES.COMPANY_MANAGER,
    email: "michael.c@company.com",
    department: "Product",
    joinDate: "2021-08-01",
    salary: 95000
  },
  {
    id: 3,
    name: "Emma Davis",
    role: ROLES.ADMIN,
    email: "emma.d@company.com",
    department: "Design",
    joinDate: "2023-01-10",
    salary: 78000
  }
];