/*
 * Created on Aug 5, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips;

import instr.*;

public class IntegerALU {
	

	
	/* register input / output */
	RegisterFile regs;
	Memory mem;
	
	WriteBackResult result = null;
	Instruction instr;
	
	int hi;
	int low;
	
	public IntegerALU(RegisterFile regs, Memory mem)
	{
		this.regs = regs;
		this.mem = mem;
	}
	
	
	
	public void execute(Instruction I)
		throws InstructionExecuteException {
	
		this.instr = I;
		setNoResult( ); 
		
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
			
			setRegResult(rd, tmp);
			break;
		
		case 2:			/* SRL - Shift Right Logical */
			tmp = regs.getReg( rs );
			tmp = tmp >> shamt;
			
			setRegResult(rd, tmp);
			break;

		case 3:		/* SRA - ?? */
			tmp = regs.getReg( rs );
			tmp = tmp >>> shamt;
			
			setRegResult(rd, tmp);
			break;
		case 4:		/* SLLV - ?? */
			tmp = regs.getReg( rt );
			shamt = regs.getReg(rs) & 31;
			tmp = tmp << shamt;
			
			setRegResult(rd, tmp);
			break;
		case 6:		/* SRLV - ?? */
			tmp = regs.getReg( rt );
			shamt = regs.getReg(rs) & 31;
			tmp = tmp >> shamt;
			
			setRegResult(rd, tmp);
			break;
		case 7:		/* SRAV - ?? */
			tmp = regs.getReg( rt );
			shamt = regs.getReg(rs) & 31;
			tmp = tmp >>> shamt;
			
			setRegResult(rd, tmp);
			break;
			
		case 8:		/* JR - Jump Register */
			setPCResult( regs.getReg(rs));
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
			
			setRegResult(rd, hi);
			break;
		
		case 17:	/* MTHI - ?? */
			break;
			
		case 18:	/* MFLO - Move from Lo */
			low = regs.getLow( );

			setRegResult(rd, low);
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
			
			setRegResult(rt, tmp);
			break;
		
		case 34:	/* SUB - subtract */
		case 35:	/* SUBU - subtract unsigned */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = aa - ab;
			
			setRegResult(rt, tmp);
			break;
		
		case 36:	/* AND */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = aa & ab;
			
			setRegResult(rt, tmp);
			break;
		
		case 37:	/* OR */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = aa | ab;
			
			setRegResult(rt, tmp);
			break;
		
		case 38:	/* XOR */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = aa ^ ab;
			setRegResult(rt, tmp);
			break;
			
		case 39:	/* NOR */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = ~(aa | ab);
			setRegResult(rt, tmp);
			break;
		
		case 42:	/* SLT - Set Less Than */
		case 43:	/* SLTU - Set Less Than Unsigned */
			aa = regs.getReg(rs);
			ab = regs.getReg(rt);
			
			tmp = (aa < ab) ? 1 : 0;
			
			setRegResult(rt, tmp);
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
			setPCResult(addr);
			break;
		case 0x03: /* JAL - Jump and Link */
			regs.setReg(31, regs.getPC());
			setPCResult(addr);
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
		int sgnimmd = imm;
		if (imm > 32767)
		{
			sgnimmd = -1 * (65536-imm);
		}
		
		int tmp = regs.getReg(rt);
		int address;
		int value;
		
		switch(I.getOp())
		{
		
		case 1:		/* BLTZ - Branch if Less than zero */
			if (regs.getReg(rs) < 0)
				setPCResult( regs.getPC() + sgnimmd);
			break;
		case 4:		/* BEQ - Branch if equal */
			if (regs.getReg(rs) == regs.getReg(rt))
				setPCResult( regs.getPC() + sgnimmd);
			break;
		case 5:		/* BNE - Branch if not equal */
			if (regs.getReg(rs) != regs.getReg(rt))
				setPCResult( regs.getPC() + sgnimmd);
			break;
		case 6:		/* BLEZ - Branch if Less than equal to zero */
			if (regs.getReg(rs) <= 0)
				setPCResult( regs.getPC() + sgnimmd);
			break;
		case 7:		/* BGTZ - Branch if greater than zero*/
			if (regs.getReg(rs) > 0)
				setPCResult( regs.getPC() + sgnimmd);
			break;
		case 8:	/* ADDI - Add Immediate */
		case 9:	/* ADDIU - Add Immediate Unsigned */
			tmp = regs.getReg(rs) + imm;
			setRegResult(rt, tmp);
			
			break;
		case 10:	/* SLTI - Set Less Than Immediate */
		case 11:	/* SLTIU - Set Less Than Immediate Unsigned */
			tmp = ( regs.getReg(rs) < sgnimmd) ? 1 : 0;
			setRegResult(rt, tmp);
			break;
		case 12:	/* ANDI - And Immediate */
			tmp = regs.getReg(rs) & imm;
			setRegResult(rt, tmp);
			break;
		case 13:	/* ORI - OR immediate */
			tmp = regs.getReg(rs) | imm;
			setRegResult(rt, tmp);
			break;
		case 14:	/* XORI - XOR immediate */
			tmp = regs.getReg(rs) ^ imm;
			setRegResult(rt, tmp);
			break;
		case 15:	/* LUI - Load upper immediate */
			tmp = imm << 16;
			setRegResult(rt, tmp);
			break;
		
		
		case 32:	/* Load Byte */
		case 36:	/* LBU - Load Byte Unsigned */
			setMemResult(rt, regs.getReg(rs) +  sgnimmd, true, WriteBackResult.BYTE);
			break;
		case 33:	/* Load Half-Word */
		case 37:	/* Load Half-Word Unsigned */
			setMemResult(rt, regs.getReg(rs) + sgnimmd, true, WriteBackResult.HWORD);
			break;
		case 35:	/* Load Word */
			setMemResult(rt, regs.getReg(rs) + sgnimmd, true, WriteBackResult.WORD);
			break;

		case 40:	/* Store WriteBackResult.BYTE */
			address = regs.getReg(rs) + sgnimmd;
			setMemResult(rt, address, false, WriteBackResult.BYTE);
			break;
			
		case 41:	/* Store Half-Word */
			address = regs.getReg(rs) + sgnimmd;
			setMemResult(rt, address, false, WriteBackResult.HWORD);
			break;
		case 43:	/* Store Word */
			address = regs.getReg(rs) + sgnimmd;
			setMemResult(rt, address, false, WriteBackResult.WORD);
			break;
			
		case 49:	/* Load Co-Processor 1 (FPU) */
			address = regs.getReg(rs) + sgnimmd;
			
			setFMemResult(rt, address, true, WriteBackResult.WORD);
			break;
			
		case 57:	/* Store Co-Processor 1 (FPU) */
			address = regs.getReg(rs) + sgnimmd;
			value = Float.floatToRawIntBits(regs.getSFreg(rt));
			//TODO handle this too
			
			setFMemResult(rt, address, false, WriteBackResult.WORD);
		}


		
	}
	
	protected void executeF(FRtype I)throws InstructionExecuteException 
	{
		int fs = I.getFs();
		int fd = I.getFd( );
		int ft = I.getFt( );

		double tmp;
		double a,b;
		boolean single = I.isSinglePrec();
		
		if (I.isSinglePrec())
		{
			a = regs.getSFreg(fs);
			b = regs.getSFreg(fd);
		}
		else
		{
			a = regs.getDFreg(fs);
			b = regs.getDFreg(fd);
		}
		
		switch(I.getFunc())
		{
		case 0:			/* ADD */
			
			tmp = a + b; 
				
			setFRegResult(ft, single, tmp);
			break;
		
		case 1:			/* SUB */
			
			tmp = a - b;
			
			setFRegResult(ft, single, tmp);
			break;
		
		case 2:			/* MUL */
			
			tmp = a * b;
			
			setFRegResult(ft, single, tmp);
			break;
		
		case 3:			/* DIV */
			
			tmp = a / b;
			
			setFRegResult(ft, single, tmp);
			break;
		case 5:			/* ABS */
			
			tmp = Math.abs(a);
			
			setFRegResult(ft, single, tmp);
			break;
		
		case 6:		/* MOV */
			
			tmp = a;
			setFRegResult(ft, single, a);
			break;
		
		case 7:		/* NEG */
			
			tmp = a * -1;
			setFRegResult(ft, single, a);
			break;
		
		case 33:	/* CVT.S */
			
			tmp = (float) a;
			setFRegResult(ft, true, a);
			break;
		
		case 34:	/* CVT.D */
			tmp = (double) a;
			setFRegResult(ft,false,a);
			break;
		default:
			throw new RuntimeException("This ALU does not support all floating point operations.");
		}
		
			
	}

	
	protected void syscall(Rtype instr)
	{
		throw new RuntimeException("This ALU does not support system calls.");
	}

	public void setRegResult(int register, int value)
	{
		this.result = WriteBackResult.setRegResult(instr, register, value); 
		
	}
	
	public void setFRegResult(int register, boolean single, double value)
	{
		this.result = WriteBackResult.setFRegResult(instr, single, register,value);
	}
	
	protected void setNoResult( )
	{
		this.result = WriteBackResult.setNoResult(instr);
	}
	
	protected void setMemResult(int address, int value, boolean load, int type)
	{
		this.result = WriteBackResult.setMemResult(instr, address,value,load,type);
	}

	protected void setFMemResult(int address, int value, boolean load, int type)
	{
		this.result = WriteBackResult.setFMemResult(instr, address,value,load,type);
	}
	
	protected void setPCResult(int address)
	{
		this.result = WriteBackResult.setPCResult(instr, address);
	}
	
	public WriteBackResult getResult( )
	{
		return result;
	}

}
