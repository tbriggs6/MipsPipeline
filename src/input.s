mul.s	$f3,$f1,$f2
add.s	$f0,$f0,$f3
addi	$s0,$s0,1
bne		$s0,$s1,-20
jr		$ra
