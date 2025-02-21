; Simulation basique d'Artificial Life avec particules
Graphics 800, 600, 32, 2
SetBuffer BackBuffer()

Const NUM_PARTICLES = 1000
Const INTERACTION_RADIUS# = 50.0
Const FORCE_STRENGTH# = 0.02
Const REPRODUCTION_RADIUS# = 30.0
Const REPRODUCTION_RATE# = 0.01

Type Particle
	Field x#, y#
	Field vx#, vy#
	Field life#
	Field TypeA
End Type

Dim particles.Particle(NUM_PARTICLES)

; Initialisation des particules
For i = 0 To NUM_PARTICLES - 1
	particles(i) = New Particle
	particles(i)\x = Rnd(0, GraphicsWidth())
	particles(i)\y = Rnd(0, GraphicsHeight())
	particles(i)\vx = Rnd(1, -1)
	particles(i)\vy = Rnd(-1, 1)
	particles(i)\life = Rnd(100, 255) ; Une valeur de vie pour chaque particule
	particles(i)\TypeA = Rand(0, 4) ; 2 types de particules
Next



While Not KeyDown(1)
	Cls
	
	; Mise à jour des particules
	For i = 0 To NUM_PARTICLES - 1
		p.Particle = particles(i)
		ax# = 0
		ay# = 0
		
		; Interaction avec d'autres particules
		For j = 0 To NUM_PARTICLES - 1
			If i <> j Then
				q.Particle = particles(j)
				dx# = q\x - p\x
				dy# = q\y - p\y
				dist# = Sqr(dx * dx + dy * dy)
				
				If dist > 0 And dist < INTERACTION_RADIUS Then
					; Attirer ou repousser
					force# = FORCE_STRENGTH / dist
					ax = ax + force * dx
					ay = ay + force * dy
				EndIf
			EndIf
		Next
		
		; Mise à jour de la vitesse et de la position
		p\vx = (p\vx + ax) * 0.95
		p\vy = (p\vy + ay) * 0.95
		p\x = p\x + p\vx
		p\y = p\y + p\vy
		
		; Si la particule sort de l'écran, la ramener
		If p\x < 0 Then p\x = GraphicsWidth()
		If p\x > GraphicsWidth() Then p\x = 0
		If p\y < 0 Then p\y = GraphicsHeight()
		If p\y > GraphicsHeight() Then p\y = 0
		
		; Réduction de la "vie" de la particule
		p\life = p\life - 0.4
		
		If p\life <= 0 Then
		
			; La particule meurt et une nouvelle particule apparaît à une position aléatoire
			p\life = Rnd(100, 255)
			p\x = Rnd(0, GraphicsWidth())
			p\y = Rnd(0, GraphicsHeight())
			p\vx = Rnd(1, -1)
			p\vy = Rnd(-1, 1)
			;End
		EndIf
		
		; Reproduction basique (si deux particules sont proches)
		If dist < REPRODUCTION_RADIUS Then
			If Rand(0.0, 1.0) < REPRODUCTION_RATE Then
		
				; Une nouvelle particule est créée au centre des deux particules
				newParticle.Particle = New Particle
				newParticle\x = (p\x + q\x) / 2
				newParticle\y = (p\y + q\y) / 2
				newParticle\vx = Rnd(-1, 1)
				newParticle\vy = Rnd(1, -1)
				newParticle\life = Rnd(100, 255)
				newParticle\TypeA = Rand(0, 4)

				
				;particles = particles + newParticle
			EndIf
		EndIf
	Next
	
	; Affichage des particules
	For i = 0 To NUM_PARTICLES - 1
		Select particles(i)\TypeA
			Case 0 : Color 255, 0, 0
			Case 1 : Color 0, 255, 0
			Case 2 : Color 255, 0, 250
			Case 3 : Color 250, 255, 0
		End Select
		Oval particles(i)\x - 2.5, particles(i)\y - 2.5, 5, 5, True
	Next
	
	Flip
Wend
End