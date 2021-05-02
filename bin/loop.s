addi	$t0, $zero, 0
slti	$t1, $t0, 5
beqz	$t1, 7
sll	$t1, $t0, 2
add	$t2, $sp, $t1
lw	$t3, 0($t2)
addi	$t3, $t3, -1
sw	$t3, 0($t2)
j -8
