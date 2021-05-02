/*
 * Created on Aug 5, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips;

import instr.*;

public class SimpleALU {

	RegisterFile regs;
	Memory mem;
	
	boolean changeMem;
	int address;
	int value;
	
	int hi;
	int low;
	
	public SimpleALU(RegisterFile regs, Memory mem)
	{
		this.regs = regs;
		this.mem = mem;
	}
	
	
	public void execute(Instruction I)
		throws InstructionExecuteException {
		
		changeMem = false;
		
		switch(I.getType()) {
		case 'R':
			executeR((Rtype) I);
			break;
		case 'J':
			executeJ((Jtype) I);
			break;
		case 'I':
			executeI((Itype) I);
			break;
		case 'F':
			executeF((FRtype) I);
			break;
		default:
			throw new InstructionExecuteException("Unknown instruction type");
		}
	}
	

	protected void executeR(Rtype I)throws InstructionExecuteException 
	{
		int tmp;
		
		int rs = I.getRs( );
		int rd = I.getRd( );
		int rt = I.getRt( );
		int shamt = I.getShamt();
		
		
		switch(I.getFunc())
		{
		case 0:			/* SLL - Shift Left Logical */
			
			tmp = regs.getReg( rs );
			tmp = tmp << shamt;
			regs.setReg(rd, tmp);
			break;
		
		case 2:			/* SRL - Shift Right Logical */
			tmp = regs.getReg( rs );
			tmp = tmp >> shamt;
			regs.setReg( rd, tmp);
			break;

		case 3:		/* SRA - ?? */
			tmp = regs.getReg( rs );
			tmp = tmp >>> shamt;
			regs.setReg( rd, tmp);
			break;
		case 4:		/* SLLV - ?? */
			tmp = regs.getReg( rt );
			shamt = regs.getReg(rs) & 31;
			tmp = tmp << shamt;
			regs.setReg( rd, tmp);
			break;
		case 6:		/* SRLV - ?? */
			tmp = regs.getReg( rt );
			shamt = regs.getReg(rs) & 31;
			tmp = tmp >> shamt;
			regs.setReg( rd, tmp);
			break;
		case 7:		/* SRAV - ?? */
			tmp = regs.getReg( rt );
			shamt = regs.getReg(rs) & 31;
			tmp = tmp >>> shamt;
			regs.setReg( rd, tmp);
			break;
			
		case 8:		/* JR - Jump Register */
			regs.setPC( regs.getReg(rs));
			break;
		
		case 9:		/* JALR */
			break;
			
		case 12: 	/* SYSCALL */
			syscall(I);
			break;
		
		case 13:
			throw new RuntimeException("BREAK instruction executed.");
			
		case 16: 	/* MFHI - Move from Hi */
			hi = regs.getHi();
			regs.setReg(rd, hi);
			break;
		
		case 17:	/* MTHI - ?? */
			break;
			
		case 18:	/* MFLO - Move from Lo */
			low = regs.getLow( );
			regs.setReg(rd, low);
			break;
		
		case 19:	/* MTLO -?? */
			break;
		
		case 24:	/* MULT - multiply */
		case 25:	/* MULTU - multiply unsigned */
			long a = regs.getReg(rs);
			long b = regs.getReg(rt);
			
			long prod = a * b;
			hi = (int)(  (prod >>32) & 0xFFFFFFFF);
			low = (int) ( prod & 0xFFFFFFFF);
			regs.setHi( hi );
			regs.setLow(low);
			
			break;  
		
		case 26:	/* DIV - divide */
		case 27:	/* DIVU - divide unsigned */
			int aa = regs.getReg(rs);
			int ab = regs.getReg(rt);
			
			hi = (int) aa / ab;
			low = (int) aa % ab;
			
			regs.setHi( hi );
			regs.setLow(low);
			break;
			
		case 32:	/* ADD - add */
		case 33:	/* ADDU - add unsigned */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = aa + ab;
			regs.setReg(rt, tmp);
			break;
		
		case 34:	/* SUB - subtract */
		case 35:	/* SUBU - subtract unsigned */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = aa - ab;
			regs.setReg(rt, tmp);
			break;
		
		case 36:	/* AND */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = aa & ab;
			regs.setReg(rt, tmp);
			break;
		
		case 37:	/* OR */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = aa | ab;
			regs.setReg(rt, tmp);
			break;
		
		case 38:	/* XOR */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = aa ^ ab;
			regs.setReg(rt, tmp);
			break;
			
		case 39:	/* NOR */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = ~(aa | ab);
			regs.setReg(rt, tmp);
			break;
		
		case 42:	/* SLT - Set Less Than */
		case 43:	/* SLTU - Set Less Than Unsigned */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = (aa < ab) ? 1 : 0;
			
			regs.setReg(rt, tmp);
			break;
			
		default:
			throw new InstructionExecuteException("Unknown R-type opcode: " + I);
		}
		
	}
	
	protected void executeJ(Jtype I)throws InstructionExecuteException 
	{
		int addr = I.getAddress();
		
		switch(I.getOp())
		{
		case 0x02:	/* J - Jump */
			regs.setPC(addr);
			break;
		case 0x03: /* JAL - Jump and Link */
			regs.setReg(31, regs.getPC());
			regs.setPC(addr);
			break;
		default:
			throw new InstructionExecuteException("Unknown J-type opcode: " + I);
		}
		
	}

	protected void executeI(Itype I)throws InstructionExecuteException 
	{
		 
		int rs = I.getRs();
		int rt = I.getRt();
		int imm = I.getAddress();
		
		int tmp = regs.getReg(rt);
		
		switch(I.getOp())
		{
		
		case 1:		/* BLTZ - Branch if Less than zero */
			if (regs.getReg(rs) < 0)
				regs.setPC( regs.getPC() + imm);
			break;
		case 4:		/* BEQ - Branch if equal */
			if (regs.getReg(rs) == regs.getReg(rt))
				regs.setPC( regs.getPC() + imm);
			break;
		case 5:		/* BNE - Branch if not equal */
			if (regs.getReg(rs) != regs.getReg(rt))
				regs.setPC( regs.getPC() + imm);
			break;
		case 6:		/* BLEZ - Branch if Less than equal to zero */
			if (regs.getReg(rs) <= 0)
				regs.setPC( regs.getPC() + imm);
			break;
		case 7:		/* BGTZ - Branch if greater than zero*/
			if (regs.getReg(rs) > 0)
				regs.setPC( regs.getPC() + imm);
			break;
		case 8:	/* ADDI - Add Immediate */
		case 9:	/* ADDIU - Add Immediate Unsigned */
			tmp = regs.getReg(rs) + imm;
			break;
		case 10:	/* SLTI - Set Less Than Immediate */
		case 11:	/* SLTIU - Set Less Than Immediate Unsigned */
			tmp = ( regs.getReg(rs) < imm) ? 1 : 0;
			break;
		case 12:	/* ANDI - And Immediate */
			tmp = regs.getReg(rs) & imm;
			break;
		case 13:	/* ORI - OR immediate */
			tmp = regs.getReg(rs) | imm;
			break;
		case 14:	/* XORI - XOR immediate */
			tmp = regs.getReg(rs) ^ imm;
			break;
		case 15:	/* LUI - Load upper immediate */
			tmp = imm << 16;
			break;
		
		
		case 32:	/* Load Byte */
		case 36:	/* LBU - Load Byte Unsigned */
			tmp = (int) mem.getByte( regs.getReg(rs) + imm);
			break;
		case 33:	/* Load Half-Word */
		case 37:	/* Load Half-Word Unsigned */
			byte aa = mem.getByte( regs.getReg(rs) + imm);
			byte ab = mem.getByte( regs.getReg(rs) + imm + 1);
			
			tmp = aa << 8 | ab;
			break;
		case 35:	/* Load Word */
			tmp = mem.getWord(regs.getReg(rs) + imm);
			break;

		case 40:	/* Store Byte */
			changeMem = true;
			address = regs.getReg(rs) + imm;
			value = regs.getReg(rt) & 0xff;
			
			mem.storeByte(address, (byte)value);
			break;
		case 41:	/* Store Half-Word */
			changeMem = true;
			address = regs.getReg(rs) + imm;
			value = regs.getReg(rt) & 0xffff;
			
			mem.storeByte(address, (byte)((value >> 8) & 0xff));
			mem.storeByte(address+1, (byte)(value & 0xff));
			break;
		case 43:	/* Store Word */
			changeMem = true;
			address = regs.getReg(rs) + imm;
			value = regs.getReg(rt);
			
			mem.storeWord(address, value);
			break;
			
		case 49:	/* Load Co-Processor 1 (FPU) */
			address = regs.getReg(rs) + imm;
			float fval = Float.intBitsToFloat(mem.getWord(address));
			regs.setSFreg(rt, fval);
			break;
		case 57:	/* Store Co-Processor 1 (FPU) */
			address = regs.getReg(rs) + imm;
			value = Float.floatToRawIntBits(regs.getSFreg(rt));
			mem.storeWord(address, value);
		}

		regs.setReg(rt, tmp);
		
	}
	
	protected void executeF(FRtype I)throws InstructionExecuteException 
	{
		throw new InstructionExecuteException("Integer ALU cannot execute FPU instructions");
	}


	/**
	 * @return Returns the address.
	 */
	public int getAddress() {
		return address;
	}


	/**
	 * @return Returns the changeMem.
	 */
	public boolean isChangedMem() {
		return changeMem;
	}


	/**
	 * @return Returns the value.
	 */
	public int getValue() {
		return value;
	}

	
	protected void syscall(Rtype instr)
	{
		throw new RuntimeException("This ALU does not support system calls.");
	}
	
}
